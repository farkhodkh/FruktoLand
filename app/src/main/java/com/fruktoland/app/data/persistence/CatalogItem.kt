package com.fruktoland.app.data.persistence

data class CatalogItem(
    var id: Long,
    var name: String,
    var category: String,
    var description: String,
    var imageAddress: String,
    var price: Double,
    var unit: String,
    var qtty: Double
)