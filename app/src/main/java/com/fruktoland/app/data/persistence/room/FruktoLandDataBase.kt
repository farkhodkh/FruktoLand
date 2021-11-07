package com.fruktoland.app.data.persistence.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fruktoland.app.data.persistence.dao.CatalogDao
import com.fruktoland.app.data.persistence.model.CatalogModule

@Database(
    entities = [
        CatalogModule::class
    ],
    version = 1
)
//@TypeConverters(Converters::class)
abstract class FruktoLandDataBase : RoomDatabase() {
    internal abstract fun catalogDao(): CatalogDao

    companion object {
        const val DATABASE_NAME = "frukto_land_db"
    }
}