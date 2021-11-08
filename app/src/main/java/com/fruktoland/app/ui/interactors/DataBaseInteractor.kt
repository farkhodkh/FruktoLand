package com.fruktoland.app.ui.view

import com.fruktoland.app.data.persistence.items.BasketItem
import com.fruktoland.app.data.persistence.items.CatalogItem
import com.fruktoland.app.data.persistence.model.CatalogModule
import com.fruktoland.app.data.persistence.model.toBasketItem
import com.fruktoland.app.data.persistence.model.toBasketModule
import com.fruktoland.app.data.persistence.model.toCatalogItem
import com.fruktoland.app.data.persistence.room.FruktoLandDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

interface DataBaseInteractor {
    fun getCatalogItem(catalogName: String?): Flow<List<CatalogItem>?>
    fun addCatalogItems(list: List<CatalogModule>)
    fun addToBasket(item: CatalogItem)
    fun addToBasket(item: BasketItem)
    fun removeFromBasket(item: CatalogItem)
    fun getAllFromBasket(): Flow<List<BasketItem>?>
}

class DataBaseInteractorImpl(private val db: FruktoLandDataBase) : DataBaseInteractor {
    val scope = CoroutineScope(Dispatchers.IO)

    override fun getCatalogItem(catalogName: String?): Flow<List<CatalogItem>?> = callbackFlow {
        val list = if (catalogName.isNullOrBlank()) {
            db
                .catalogDao()
                .getAllCatalog()

        } else {
            db
                .catalogDao()
                .getAllCatalogByName(catalogName)
        }

        if (list.isNullOrEmpty())
            close(Throwable("Empty list"))

        val basketList = db
            .basketDao()
            .getAllItems()

        offer(
            list?.let { listModules ->
                listModules.map {
                    val qtty = basketList?.firstOrNull { item -> it.id == item.id }?.qtty ?: 0.0
                    it.qtty = qtty
                    it.toCatalogItem()
                }
            }
        )
        awaitClose { }
    }

    override fun addCatalogItems(list: List<CatalogModule>) {
        scope.launch(Dispatchers.IO) {
            list.forEach { catalogModule ->
                db.catalogDao().insert(catalogModule)
            }
        }
    }

    override fun addToBasket(item: CatalogItem) {
        scope.launch {
            db.basketDao().insert(item.toBasketModule())
        }
    }

    override fun removeFromBasket(item: CatalogItem) {
        scope.launch {
            db.basketDao().remove(item.toBasketModule())
        }
    }

    override fun getAllFromBasket(): Flow<List<BasketItem>?> = callbackFlow {
        offer(
            db
                .basketDao()
                .getAllItems()?.map { it.toBasketItem() }.orEmpty()
        )

        awaitClose { }
    }

    override fun addToBasket(item: BasketItem) {
        scope.launch {
            db.basketDao().insert(item.toBasketModule())
        }
    }
}