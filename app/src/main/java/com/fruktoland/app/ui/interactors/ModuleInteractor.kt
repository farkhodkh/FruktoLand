package com.fruktoland.app.ui.view

import com.fruktoland.app.common.Const
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
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import okhttp3.*
import org.slf4j.LoggerFactory


interface ModuleInteractor {
    suspend fun getCatalogItems(catalogName: String?): List<CatalogItem>
    suspend fun addCatalogItems(list: List<CatalogModule>)
    fun addToBasket(item: CatalogItem)
    fun addToBasket(item: BasketItem)
    fun removeFromBasket(item: CatalogItem)
    fun removeFromBasket(item: BasketItem)
    fun getAllFromBasket(): Flow<List<BasketItem>?>
    fun confirmOrder(order: ClientOrderRequest): Flow<Boolean>
}

class ModuleInteractorImpl(
    private val db: FruktoLandDataBase,
    private val apiService: FruktoLandApiService
) : ModuleInteractor {
    val scope = CoroutineScope(Dispatchers.IO)
    val logger = LoggerFactory.getLogger(this::class.java.canonicalName)
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
                catalogItems.firstOrNull { item -> basketItem.id == item.id }?.qtty =
                    basketItem.qtty
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

    override fun confirmOrder(order: ClientOrderRequest): Flow<Boolean> = callbackFlow {
        scope.launch {
            //TODO - Не работает с ретрофит, не видит адреса
//            val responceOrder = apiService
//                .service
//                .sendOrder(order)
//                .await()
//
//            if (responceOrder.isSuccessful) {
//                //db.basketDao().removeAllItems()
//                val b = 0
//            }

            val client = OkHttpClient().newBuilder()
                .build()
            val mediaType: MediaType =
                MediaType.parse("application/json") ?: MediaType.get("application/json")
            val body = RequestBody.create(mediaType, Gson().toJson(order))
            val request: Request = Request.Builder()
                .url("http://217.174.101.27/frukto-land/hs/exchange/fruktoland/receiveOrder")
                .method("POST", body)
                .addHeader("Authorization", "Basic " + Const.TOKEN)
                .addHeader("Content-Type", "application/json")
                .build()
            val clientResponse: Response = client.newCall(request).execute()

            if (clientResponse.isSuccessful) {
                val responseBody = clientResponse.body()
                logger.error("responseBody ${responseBody.toString()}")
                db.basketDao().removeAllItems()
                offer(true)
            } else {
                offer(false)
            }
        }
        awaitClose { }
    }
}