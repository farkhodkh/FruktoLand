package com.fruktoland.app.data.api

import com.fruktoland.app.data.mapper.CatalogItemResponce
import com.fruktoland.app.data.mapper.ClientOrderRequest
import com.fruktoland.app.data.mapper.ClientOrderResultResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.*
import java.util.concurrent.Flow

interface FruktoLandApi {
    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET("/frukto-land/hs/exchange/fruktoland/getstock/")
    suspend fun getStockByCatalogName(@Query("catalogName") catalogName: String): Response<CatalogItemResponce>

    @POST("(/frukto-land/hs/exchange/fruktoland/receiveOrder/)")
    suspend fun sendOrder(@Body order: ClientOrderRequest): Deferred<Response<ClientOrderResultResponse>>
}