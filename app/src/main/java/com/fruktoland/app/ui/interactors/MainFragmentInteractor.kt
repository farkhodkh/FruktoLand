package com.fruktoland.app.ui.view

import com.fruktoland.app.data.mapper.CatalogItemMapper
import com.fruktoland.app.data.persistence.model.CatalogModule
import com.fruktoland.app.data.persistence.room.FruktoLandDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

interface MainFragmentInteractor {
    fun getCatalogItem(catalogName: String?): Flow<List<CatalogItemMapper>>
    fun addCatalogItems(list: List<CatalogModule>)
}

class MainFragmentInteractorImpl(private val db: FruktoLandDataBase) : MainFragmentInteractor {
    val scope = CoroutineScope(Dispatchers.IO)

    override fun getCatalogItem(catalogName: String?): Flow<List<CatalogModule>> = callbackFlow {
        offer(listOf(CatalogModule(
            0L,
            "Фрукты",
            "fruits",
            "Витанимы",
            "",
            12.78,
            "шт.",
            12.65
        )))
        awaitClose { }
    }

    override fun addCatalogItems(list: List<CatalogModule>) {

    }
}