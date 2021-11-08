package com.fruktoland.app.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fruktoland.app.ui.state.*
import com.fruktoland.app.ui.view.DataBaseInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import org.slf4j.LoggerFactory
import javax.inject.Inject

@HiltViewModel
class OrderFragmentViewModel @Inject constructor(private var interactor: DataBaseInteractor) :
    ViewModel() {
    val logger = LoggerFactory.getLogger(this::class.java.canonicalName)

    private val _state = MutableStateFlow<OrderFragmentState>(OrderDefault("Default state"))
    val state: StateFlow<OrderFragmentState> = _state

    fun getAllBasket() {
        interactor
            .getAllFromBasket()
            .catch { error ->
                error.localizedMessage?.let { errorMessage ->
                    logger.error(errorMessage)
                    if (errorMessage.contentEquals("Empty list"))
                        _state.tryEmit(OrderEmpty("Список заказов пуст"))
                    else
                        _state.tryEmit(OrderError("Ошибка при обновлении списка корзины"))
                }
            }
            .onEach { list ->
                list?.let {
                    _state.tryEmit(OrderDataUpdate("Список обновлен", it))
                }
            }
            .launchIn(viewModelScope)
    }
}