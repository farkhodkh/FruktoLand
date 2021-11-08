package com.fruktoland.app.common

import com.fruktoland.app.data.persistence.items.CatalogItem

object Const {
    fun getEmptyList(): List<CatalogItem> = listOf(
        CatalogItem(
            1L,
            "Клубника",
            "berries",
            "Вкусная ягода",
            "ic_no_phote",
            10.00,
            "кг.",
            0.0
        ),
        CatalogItem(
            2L,
            "Яблоко (Годен) выращено от чистого сердце",
            "fruits",
            "Источник железа",
            "ic_no_phote",
            220.0,
            "кг.",
            0.0
        ),
        CatalogItem(
            3L,
            "Арбуз (Азербайджанские)",
            "fruits",
            "Источник железа",
            "ic_no_phote",
            120.0,
            "кг.",
            0.0
        )
    )
}