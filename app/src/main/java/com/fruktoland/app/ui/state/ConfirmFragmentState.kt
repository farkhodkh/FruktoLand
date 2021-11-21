package com.fruktoland.app.ui.state

sealed class ConfirmFragmentState

 class ConfirmError(val description: String): ConfirmFragmentState()
 class ConfirmDefault(): ConfirmFragmentState()
 class ConfirmUploading(): ConfirmFragmentState()
 class ConfirmOrderSendStates(val statue: Boolean): ConfirmFragmentState()