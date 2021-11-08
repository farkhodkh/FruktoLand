package com.fruktoland.app.ui.viewModel

import androidx.lifecycle.ViewModel
import com.fruktoland.app.ui.state.MainDefault
import com.fruktoland.app.ui.state.MainFragmentState
import com.fruktoland.app.ui.view.DataBaseInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.slf4j.LoggerFactory
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(
    private var mainInteractor: DataBaseInteractor
) : ViewModel() {
    val logger = LoggerFactory.getLogger(this::class.java.canonicalName)
    private val _state = MutableStateFlow<MainFragmentState>(MainDefault("Default state"))
    val state: StateFlow<MainFragmentState> = _state

}