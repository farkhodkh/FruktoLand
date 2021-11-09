package com.fruktoland.app.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fruktoland.app.data.mapper.ClientOrderRequest
import com.fruktoland.app.data.persistence.model.toBasketModule
import com.fruktoland.app.data.persistence.model.toCatalogItemMapper
import com.fruktoland.app.ui.state.OrderDataUpdate
import com.fruktoland.app.ui.state.OrderEmpty
import com.fruktoland.app.ui.state.OrderError
import com.fruktoland.app.ui.view.ModuleInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory
import javax.inject.Inject

@HiltViewModel
class ConfirmFragmentViewModel@Inject constructor(private var interactor: ModuleInteractor) : ViewModel() {


    fun confirmOrder(
        name: String,
        address: String,
        phone: String,
        comments: String
    ) {

        val logger = LoggerFactory.getLogger(this::class.java.canonicalName)
        viewModelScope.launch {
            interactor
                .getAllFromBasket()
                .catch { error ->
                    error.localizedMessage?.let { errorMessage ->
                        logger.error(errorMessage)
                    }
                }
                .collect { list ->
                    val order = ClientOrderRequest(
                        name = name, address = address, phone = phone, comments = comments, orderList = list?.map { it.toCatalogItemMapper() }.orEmpty()
                    )

                    interactor.confirmOrder(order)
                }
        }
    }
}