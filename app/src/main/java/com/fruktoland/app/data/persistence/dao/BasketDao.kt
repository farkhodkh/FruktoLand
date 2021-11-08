package com.fruktoland.app.data.persistence.dao

import androidx.room.*
import com.fruktoland.app.data.persistence.model.BasketModule
import com.fruktoland.app.data.persistence.model.CatalogModule

@Dao
internal interface BasketDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: BasketModule): Long

    @Delete
    suspend fun remove(item: BasketModule)

    @Query("SELECT * FROM ${BasketModule.Database.TABLE_NAME} ORDER BY ${BasketModule.Database.COL_NAME}")
    suspend fun getAllItems(): List<BasketModule>?
}