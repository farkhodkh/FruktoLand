package com.fruktoland.app.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fruktoland.app.data.mapper.ClientOrderRequest
import com.fruktoland.app.data.persistence.model.toCatalogItemMapper
import com.fruktoland.app.ui.state.*
import com.fruktoland.app.ui.view.ModuleInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory
import javax.inject.Inject

@HiltViewModel
class ConfirmFragmentViewModel @Inject constructor(private var interactor: ModuleInteractor) :
    ViewModel() {
    val logger = LoggerFactory.getLogger(this::class.java.canonicalName)
    private val _state = MutableStateFlow<ConfirmFragmentState>(ConfirmDefault())
    val state: StateFlow<ConfirmFragmentState> = _state

    fun confirmOrder(
        name: String,
        address: String,
        phone: String,
        comments: String
    ) {

        if (name.isEmpty()) {
            _state.tryEmit(ConfirmError("Поле \"Получатель\" не заполнено"))
            return
        }

        if (address.isEmpty()) {
            _state.tryEmit(ConfirmError("Поле \"Адресс\" не заполнено"))
            return
        }

        viewModelScope.launch {

            _state.tryEmit(ConfirmUploading())

            interactor
                .getAllFromBasket()
                .catch { error ->
                    error.localizedMessage?.let { errorMessage ->
                        logger.error(errorMessage)
                    }
                }
                .collect { list ->
                    val order = ClientOrderRequest(
                        name = name,
                        address = address,
                        phone = phone,
                        comments = comments,
                        orderList = list?.map { it.toCatalogItemMapper() }.orEmpty()
                    )
                    interactor
                        .confirmOrder(order)
                        .catch { error ->
                            error.localizedMessage?.let { errorMessage ->
                                logger.error(errorMessage)
                            }
                        }
                        .collect { result ->
                            _state.tryEmit(ConfirmOrderSendStates(true))
                        }
                }
        }
    }

    fun setDefaultState() {
        _state.tryEmit(ConfirmDefault())
    }
}