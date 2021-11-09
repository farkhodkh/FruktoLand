package com.fruktoland.app.ui.view

import com.fruktoland.app.data.api.FruktoLandApiService
import com.fruktoland.app.data.mapper.ClientOrderRequest
import com.fruktoland.app.data.mapper.toCatalogItem
import com.fruktoland.app.data.persistence.items.BasketItem
import com.fruktoland.app.data.persistence.items.CatalogItem
import com.fruktoland.app.data.persistence.model.CatalogModule
import com.fruktoland.app.data.persistence.model.toBasketItem
import com.fruktoland.app.data.persistence.model.toBasketModule
import com.fruktoland.app.data.persistence.model.toCatalogModule
import com.fruktoland.app.data.persistence.room.FruktoLandDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

interface ModuleInteractor {
    suspend fun getCatalogItems(catalogName: String?): List<CatalogItem>
    suspend fun addCatalogItems(list: List<CatalogModule>)
    fun addToBasket(item: CatalogItem)
    fun addToBasket(item: BasketItem)
    fun removeFromBasket(item: CatalogItem)
    fun removeFromBasket(item: BasketItem)
    fun getAllFromBasket(): Flow<List<BasketItem>?>
    fun confirmOrder(order: ClientOrderRequest)
}

class ModuleInteractorImpl(
    private val db: FruktoLandDataBase,
    private val apiService: FruktoLandApiService
) : ModuleInteractor {
    val scope = CoroutineScope(Dispatchers.IO)

    override suspend fun getCatalogItems(catalogName: String?): List<CatalogItem> {
        val response = apiService
            .service.getStockByCatalogName(catalogName ?: "")

        if (response.isSuccessful) {
            val catalogItems = response.body()?.stock_lines?.map {
                it.toCatalogItem()
            }.orEmpty()

            scope.launch {
                val catalogModules = catalogItems.map { it.toCatalogModule() }
                addCatalogItems(catalogModules)
            }

            val basketList = db
                .basketDao()
                .getAllItems()

            basketList?.map { basketItem ->
                catalogItems.firstOrNull { item -> basketItem.id == item.id }?.qtty = basketItem.qtty
            }

            return catalogItems
        }
        return emptyList()
    }

    override suspend fun addCatalogItems(list: List<CatalogModule>) {
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

    override fun removeFromBasket(item: BasketItem) {
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

    override fun confirmOrder(order: ClientOrderRequest) {
        scope.launch {
            val responce = apiService
                .service
                .sendOrder(order)
                .await()
            if (responce.isSuccessful) {
                db.basketDao().removeAllItems()
            }
        }
    }
}