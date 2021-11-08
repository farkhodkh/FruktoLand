package com.fruktoland.app.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fruktoland.app.common.CatalogNames
import com.fruktoland.app.common.Const
import com.fruktoland.app.data.persistence.model.toCatalogModule
import com.fruktoland.app.ui.state.*
import com.fruktoland.app.ui.view.DataBaseInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import org.slf4j.LoggerFactory
import javax.inject.Inject

@HiltViewModel
class CatalogFragmentViewModel @Inject constructor(private var interactor: DataBaseInteractor) : ViewModel() {

    private val _state = MutableStateFlow<CatalogFragmentState>(CatalogDefault("Default state"))
    val state: StateFlow<CatalogFragmentState> = _state

    fun getCatalogItems(catalogName: CatalogNames?) {
         interactor
                .getCatalogItem(catalogName?.name)
                .catch { error ->
                    error.localizedMessage?.let { errorMessage ->
                        logger.error(errorMessage)
                        if (errorMessage.contentEquals("Empty list"))
                            _state.tryEmit(CatalogEmpty("Каталог в процессе пополнения", Const.getEmptyList()))
                        else
                            _state.tryEmit(CatalogError("Ошибка при обновлении каталога"))
                    }
                }
                .onEach { list ->
                    logger.debug("Catalog data updated: $list")
                    _state.tryEmit(CatalogDataUpdate("Data updated", list.orEmpty()))
                }
                .launchIn(viewModelScope)
    }

    fun addCatalogItems(catalogName: CatalogNames?) {
        interactor
            .addCatalogItems(Const.getEmptyList().map { it.toCatalogModule() })
    }

    val logger = LoggerFactory.getLogger(this::class.java.canonicalName)
}