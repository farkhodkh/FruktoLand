package com.fruktoland.app.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fruktoland.app.ui.state.CatalogDataUpdate
import com.fruktoland.app.ui.state.Error
import com.fruktoland.app.ui.state.MainFragmentState
import com.fruktoland.app.ui.state.Default
import com.fruktoland.app.ui.view.MainFragmentInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import org.slf4j.LoggerFactory
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(
    private var mainInteractor: MainFragmentInteractor
) : ViewModel() {
    val logger = LoggerFactory.getLogger(this::class.java.canonicalName)
    private val _state = MutableStateFlow<MainFragmentState>(Default("Default state"))
    val state: StateFlow<MainFragmentState> = _state

}