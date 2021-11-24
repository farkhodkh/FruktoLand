package com.fruktoland.app.data.mapper

import com.fruktoland.app.data.persistence.items.CatalogHolderItem

class CatalogResponce(
    var catalogs: List<CatalogMapper>
)

class CatalogMapper(
    var group_code: String?,
    var group_name: String?,
    var group_description: String?,
    var group_id: String?,
    var image_address: String?,
    var order: String?
)

fun CatalogMapper.toCatalogHolderItem() = CatalogHolderItem(
    this.group_code?.toLong() ?: 0L,
    this.group_name ?: "",
    this.group_description ?: "",
    this.group_id ?: "",
    this.image_address ?: "",
    this.order?.toInt() ?: 99
)