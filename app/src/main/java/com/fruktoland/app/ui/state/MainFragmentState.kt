package com.fruktoland.app.ui.state

sealed class MainFragmentState(var description: String)

class MainDefault(description: String):MainFragmentState(description)
class MainError(description: String):MainFragmentState(description)
