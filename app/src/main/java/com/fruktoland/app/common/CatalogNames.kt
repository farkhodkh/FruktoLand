package com.fruktoland.app.common

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

sealed class CatalogNames(var name: String, val nameRu: String): Parcelable

@Parcelize
data class Barries(val catalogName: String = "berries", val catalogNameRu: String = "Ягоды") : CatalogNames(catalogName, catalogNameRu)
@Parcelize
data class Fruits(val catalogName: String = "fruits", val catalogNameRu: String = "Фрукты") : CatalogNames(catalogName, catalogNameRu)
@Parcelize
data class Vegetables(val catalogName: String = "vegetables", val catalogNameRu: String = "Овощи") : CatalogNames(catalogName, catalogNameRu)
@Parcelize
data class Salads(val catalogName: String = "salads", val catalogNameRu: String = "Салаты, грибы и пр.") : CatalogNames(catalogName, catalogNameRu)
@Parcelize
data class Meats(val catalogName: String = "meat", val catalogNameRu: String = "Мясо") : CatalogNames(catalogName, catalogNameRu)
@Parcelize
data class Nuts(val catalogName: String = "nuts", val catalogNameRu: String = "Сухофрукты") : CatalogNames(catalogName, catalogNameRu)
@Parcelize
data class Other(val catalogName: String = "others", val catalogNameRu: String = "Прочее") : CatalogNames(catalogName, catalogNameRu)
@Parcelize
data class Unknown(val catalogName: String = "unknown", val catalogNameRu: String = "Каталог товаров") : CatalogNames(catalogName, catalogNameRu)