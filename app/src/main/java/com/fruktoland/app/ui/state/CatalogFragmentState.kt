package com.fruktoland.app.ui.state

import com.fruktoland.app.data.mapper.CatalogItemMapper

sealed class CatalogFragmentState(var description: String)

class CatalogDefault(description: String):CatalogFragmentState(description)
class CatalogError(description: String):CatalogFragmentState(description)
class CatalogDataUpdate(description: String, var itemsList: List<CatalogItemMapper>):CatalogFragmentState(description)