package com.fruktoland.app.common

import com.fruktoland.app.data.persistence.items.CatalogItem

object Const {
    const val BASE_URL = "http://217.174.101.27/"
    const val TOKEN = "bmV0d29yazpXRnVCWVdOdE5Y"
    fun getEmptyList(): List<CatalogItem> = listOf(
        CatalogItem(
            1L,
            "",
            "berries",
            "Вкусная ягода",
            "ic_no_phote",
            0.00,
            "кг.",
            0.0
        ),
        CatalogItem(
            2L,
            "",
            "fruits",
            "Источник железа",
            "ic_no_phote",
            0.0,
            "кг.",
            0.0
        ),
        CatalogItem(
            3L,
            "",
            "fruits",
            "Источник железа",
            "ic_no_phote",
            0.0,
            "кг.",
            0.0
        )
    )
}