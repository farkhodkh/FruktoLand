package com.fruktoland.app.data.api

import com.fruktoland.app.data.mapper.CatalogItemResponce
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface FruktoLandApi {
    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET("/frukto-land/hs/exchange/fruktoland/getstock/")
    suspend fun getStockByCatalogName(@Query("catalogName") catalogName: String): Response<CatalogItemResponce>
}