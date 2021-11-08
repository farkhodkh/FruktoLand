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
//            val client = OkHttpClient().newBuilder()
//                .build()
//            val request: Request = Builder()
//                .url("http://217.174.101.27/frukto-land/hs/exchange/fruktoland/getstock?catalogName=fruits")
//                .method("GET", null)
//                .addHeader("Authorization", "Basic bmV0d29yazpXRnVCWVdOdE5Y")
//                .build()
//            val response = client.newCall(request).execute()

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
//                    .sslSocketFactory(sslSocketFactory)
//                    .hostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER)
                    .build()

                return okHttpClient
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }
    }
}


//                val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
//                    @Throws(CertificateException::class)
//                    override fun checkClientTrusted(
//                        chain: Array<java.security.cert.X509Certificate>,
//                        authType: String
//                    ) {
//                    }
//
//                    @Throws(CertificateException::class)
//                    override fun checkServerTrusted(
//                        chain: Array<java.security.cert.X509Certificate>,
//                        authType: String
//                    ) {
//                    }
//
//                    override fun getAcceptedIssuers(): Array<X509Certificate?> {
//                        return arrayOfNulls(0)
//                    }
//                })
//
//                val sslContext = SSLContext.getInstance("TLS")
//                sslContext.init(
//                    null, trustAllCerts,
//                    java.security.SecureRandom()
//                )
//                val sslSocketFactory = sslContext
//                    .socketFactory