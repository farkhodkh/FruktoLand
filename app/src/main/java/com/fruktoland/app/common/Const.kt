package com.fruktoland.app.common

import com.fruktoland.app.data.persistence.CatalogItem

object Const {
    fun getEmptyList(): List<CatalogItem> = listOf(
        CatalogItem(
            0L,
            "Арбуз",
            "Ягода",
            "Вкусная ягода",
            "ic_no_phote",
            150.00,
            "кг.",
            1.20
        ),
        CatalogItem(
            0L,
            "Яблоки (Голден)",
            "Фрукты",
            "Источник железа",
            "ic_no_phote",
            185.45,
            "кг.",
            23.11
        ),
        CatalogItem(
            0L,
            "Манго",
            "Фрукты",
            "Вкусная ягода",
            "ic_no_phote",
            150.00,
            "шт.",
            1.0
        ),
        CatalogItem(
            0L,
            "Яблоки (Голден)",
            "Фрукты",
            "Источник железа",
            "ic_no_phote",
            185.45,
            "шт.",
            3.0
        ),
        CatalogItem(
            0L,
            "Клубника",
            "Ягода",
            "Кладизь витаминов",
            "ic_no_phote",
            160.67,
            "кг.",
            0.0
        )
    )
}