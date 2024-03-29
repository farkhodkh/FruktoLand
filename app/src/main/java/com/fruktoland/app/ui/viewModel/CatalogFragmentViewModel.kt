package com.fruktoland.app.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fruktoland.app.common.Const
import com.fruktoland.app.data.persistence.items.CatalogItem
import com.fruktoland.app.ui.state.*
import com.fruktoland.app.ui.view.ModuleInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory
import javax.inject.Inject

@HiltViewModel
class CatalogFragmentViewModel @Inject constructor(private var interactor: ModuleInteractor) : ViewModel() {

    private val _state = MutableStateFlow<CatalogFragmentState>(CatalogDefault("Default state"))
    val state: StateFlow<CatalogFragmentState> = _state

    fun getCatalogItems(catalogId: String?) {
        viewModelScope.launch {
            val catalogList: List<CatalogItem> = interactor.getCatalogItems(catalogId)

            if (catalogList.isNotEmpty()) {
                _state.tryEmit(CatalogDataUpdate("Каталог обновлен", catalogList))
            } else {
                _state.tryEmit(CatalogEmpty("Каталог в процессе пополнения", Const.getEmptyList()))
            }
        }
    }

    val logger = LoggerFactory.getLogger(this::class.java.canonicalName)
}