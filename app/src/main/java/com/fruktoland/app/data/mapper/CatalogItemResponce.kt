package com.fruktoland.app.data.mapper

import com.fruktoland.app.data.persistence.items.CatalogItem

class CatalogItemResponce(
    var stock_lines: List<CatalogItemMapper>
)

class CatalogItemMapper(
    var stock_name: String?,
    var good_name: String?,
    var good_group_name: String?,
    var good_qtty: String?,
    var good_code: String?,
    var good_art: String?,
    var good_unit: String?,
    var image_address: String?,
    var good_price: String?
)

fun CatalogItemMapper.toCatalogItem() = CatalogItem(
    this.good_code?.toLong() ?: 0L,
    this.good_name ?: "",
    this.good_group_name ?: "",
    "",
    this.image_address ?: "",
    this.good_price?.toDoubleOrNull() ?: 0.0,
    this.good_unit ?: "",
    this.good_qtty?.toDoubleOrNull() ?: 0.0
)