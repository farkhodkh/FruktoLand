package com.fruktoland.app.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fruktoland.app.common.CatalogNames
import com.fruktoland.app.ui.state.*
import com.fruktoland.app.ui.view.MainFragmentInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import org.slf4j.LoggerFactory
import javax.inject.Inject

@HiltViewModel
class CatalogFragmentViewModel @Inject constructor(private var mainInteractor: MainFragmentInteractor) : ViewModel() {

    private val _state = MutableStateFlow<CatalogFragmentState>(CatalogDefault("Default state"))
    val state: StateFlow<CatalogFragmentState> = _state

    fun getCatalogItems(catalogName: CatalogNames?) {
         mainInteractor
                .getCatalogItem(catalogName?.name)
                .catch { error ->
                    error.localizedMessage?.let { errorMessage ->
                        logger.error(errorMessage)
                        _state.tryEmit(CatalogError(errorMessage))
                    }
                }
                .onEach { list ->
                    logger.debug("Catalog data updated: $list")
                    _state.tryEmit(CatalogDataUpdate("Data updated", list))
                }
                .launchIn(viewModelScope)
    }

    val logger = LoggerFactory.getLogger(this::class.java.canonicalName)
}