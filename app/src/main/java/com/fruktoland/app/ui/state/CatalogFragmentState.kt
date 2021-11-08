package com.fruktoland.app.ui.state

import com.fruktoland.app.data.persistence.items.CatalogItem

sealed class CatalogFragmentState(var description: String)

class CatalogDefault(description: String) : CatalogFragmentState(description)
class CatalogError(description: String) : CatalogFragmentState(description)
class CatalogEmpty(description: String, var itemsList: List<CatalogItem>) : CatalogFragmentState(description)
class CatalogDataUpdate(description: String, var itemsList: List<CatalogItem>) :
    CatalogFragmentState(description)