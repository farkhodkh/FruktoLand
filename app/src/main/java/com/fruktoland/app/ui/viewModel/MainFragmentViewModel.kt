package com.fruktoland.app.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fruktoland.app.common.Const
import com.fruktoland.app.data.persistence.items.CatalogHolderItem
import com.fruktoland.app.data.persistence.items.CatalogItem
import com.fruktoland.app.ui.state.*
import com.fruktoland.app.ui.view.ModuleInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(
    private var mainInteractor: ModuleInteractor
) : ViewModel() {
    val logger = LoggerFactory.getLogger(this::class.java.canonicalName)
    private val _state = MutableStateFlow<MainFragmentState>(MainDefault("Default state"))
    val state: StateFlow<MainFragmentState> = _state

    fun getCatalogs() {
        viewModelScope.launch {
            val catalogList: List<CatalogHolderItem> = mainInteractor.getCatalogs()

            if (catalogList.isNotEmpty()) {
                _state.tryEmit(MainDataUpdate("Каталог обновлен", catalogList.sortedBy { it.order }))
            } else {
                _state.tryEmit(MainEmpty("Каталог в процессе пополнения"))
            }
        }
    }
}