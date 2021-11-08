package com.fruktoland.app.ui.viewModel

import androidx.lifecycle.ViewModel
import com.fruktoland.app.ui.view.DataBaseInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ConfirmFragmentViewModel@Inject constructor(private var interactor: DataBaseInteractor) : ViewModel() {
}