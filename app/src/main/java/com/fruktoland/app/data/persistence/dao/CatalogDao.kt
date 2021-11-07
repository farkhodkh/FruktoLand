package com.fruktoland.app.data.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fruktoland.app.data.persistence.model.CatalogModule

@Dao
internal interface CatalogDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(config: CatalogModule): Long

    @Query("SELECT * FROM ${CatalogModule.Database.TABLE_NAME} WHERE ${CatalogModule.Database.COL_CATEGORY} = :categoryName ORDER BY ${CatalogModule.Database.COL_NAME}")
    suspend fun getAllCatalogByName(categoryName: String): List<CatalogModule>?
}