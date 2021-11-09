package com.fruktoland.app.data.mapper

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ClientOrderRequest(
    @SerializedName("client_name")
    val name: String,
    val address: String,
    val phone: String,
    val comments: String,
    val orderList: List<CatalogItemMapper>
)