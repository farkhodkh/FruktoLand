package com.fruktoland.app.data.persistence.items

data class CatalogHolderItem(
    var id: Long,
    var name: String,
    var catalogDescription: String,
    var catalog_id: String,
    var imageAddress: String,
    var order: Int
)