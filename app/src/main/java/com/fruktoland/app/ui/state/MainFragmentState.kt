package com.fruktoland.app.ui.state

import com.fruktoland.app.data.persistence.items.CatalogHolderItem

sealed class MainFragmentState(var description: String)

class MainDefault(description: String):MainFragmentState(description)
class MainError(description: String):MainFragmentState(description)
class MainEmpty(description: String):MainFragmentState(description)
class MainDataUpdate(description: String, var catalogs: List<CatalogHolderItem>):MainFragmentState(description)
