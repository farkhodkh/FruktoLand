package com.fruktoland.app.data.api

import com.fruktoland.app.data.api.FruktoLandApiService.Companion.version
import com.fruktoland.app.data.mapper.CatalogItemResponce
import com.fruktoland.app.data.mapper.CatalogResponce
import com.fruktoland.app.data.mapper.ClientOrderRequest
import com.fruktoland.app.data.mapper.ClientOrderResultResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.*

interface FruktoLandApi {
//    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET("/frukto-land/hs/exchange/fl/$version/getstock/")
    suspend fun getStockByCatalogId(@Query("catalogId") catalogId: String): Response<CatalogItemResponce>

//    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("(/frukto-land/hs/exchange/fruktoland/receiveOrder")
    suspend fun sendOrder(@Query("order") order: ClientOrderRequest): Deferred<Response<ClientOrderResultResponse>>

    @GET("/frukto-land/hs/exchange/fl/$version/getcatalog/")
    suspend fun getCatalogs(): Response<CatalogResponce>
}