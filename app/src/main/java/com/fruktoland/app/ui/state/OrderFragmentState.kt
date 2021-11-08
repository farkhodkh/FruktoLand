package com.fruktoland.app.ui.state

import com.fruktoland.app.data.persistence.items.BasketItem

sealed class OrderFragmentState(var description: String)

class OrderDefault(description: String) : OrderFragmentState(description)
class OrderError(description: String) : OrderFragmentState(description)
class OrderEmpty(description: String) : OrderFragmentState(description)
class OrderDataUpdate(description: String, var itemsList: List<BasketItem>) :
    OrderFragmentState(description)