package com.fruktoland.app.data.persistence.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fruktoland.app.data.mapper.CatalogItemMapper
import com.fruktoland.app.data.persistence.items.BasketItem

@Entity(tableName = BasketModule.Database.TABLE_NAME)
data class BasketModule(
    @PrimaryKey
    @ColumnInfo(name = Database.COL_ID)
    var id: Long,
    @ColumnInfo(name = Database.COL_NAME)
    var name: String,
    @ColumnInfo(name = Database.COL_CATEGORY)
    var category: String,
    @ColumnInfo(name = Database.COL_DESCRIPTION)
    var description: String,
    @ColumnInfo(name = Database.COL_IMAGE_ADDRESS)
    var imageAddress: String,
    @ColumnInfo(name = Database.COL_PRICE)
    var price: Double,
    @ColumnInfo(name = Database.COL_UNIT)
    var unit: String,
    @ColumnInfo(name = Database.COL_QTTY)
    var qtty: Double
) {
    object Database {
        const val TABLE_NAME = "frukto_land_basket_items"

        const val COL_ID = "id"
        const val COL_NAME = "name"
        const val COL_CATEGORY = "category"
        const val COL_DESCRIPTION = "description"
        const val COL_IMAGE_ADDRESS = "image_address"
        const val COL_PRICE = "price"
        const val COL_UNIT = "unit"
        const val COL_QTTY = "qtty"
    }
}

fun BasketItem.toBasketModule() = BasketModule(
    this.id,
    this.name,
    this.category,
    this.description,
    this.imageAddress,
    this.price,
    this.unit,
    this.qtty
)

fun BasketModule.toBasketItem() = BasketItem(
    this.id,
    this.name,
    this.category,
    this.description,
    this.imageAddress,
    this.price,
    this.unit,
    this.qtty
)

fun BasketItem.toCatalogItemMapper() = CatalogItemMapper(
    "",
    this.name,
    this.category,
    this.qtty.toString(),
    this.id.toString(),
    "",
    "",
    "",
    this.price.toString()
)