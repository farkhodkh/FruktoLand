package com.fruktoland.app.data.api

import com.fruktoland.app.common.Const
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import org.slf4j.LoggerFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FruktoLandApiService {
    @JvmField
    var service: FruktoLandApi

    init {
        service = getApiService()
    }

    val logger = LoggerFactory.getLogger(this::class.java.canonicalName)

    companion object {
        private val authInterceptor = object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val newRequest = chain.request()
                    .newBuilder()
                    .addHeader("Authorization", "Basic " + Const.TOKEN)
                    .build()

                return chain.proceed(newRequest)
            }
        }

        fun getApiService(): FruktoLandApi {
            val gson: Gson = GsonBuilder().setLenient().create()
            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(Const.BASE_URL)
                .client(getUnsafeOkHttpClient())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            val request = retrofit.create(FruktoLandApi::class.java)
            return request
        }

        fun getUnsafeOkHttpClient(): OkHttpClient {
            try {
                var okHttpClient = OkHttpClient()
                okHttpClient = okHttpClient.newBuilder()
                    .addInterceptor(authInterceptor)
                    .build()

                return okHttpClient
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }
    }
}