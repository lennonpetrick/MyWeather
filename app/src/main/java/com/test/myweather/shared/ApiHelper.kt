package com.test.myweather.shared

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory



class ApiHelper private constructor(
        val token: String,
        interceptor: Interceptor
) {

    private val endpoint = "https://api.openweathermap.org/data/2.5/"
    private val retrofit: Retrofit

    companion object {
        private var INSTANCE: ApiHelper? = null

        @JvmStatic
        @Synchronized
        fun getInstance(token: String, interceptor: Interceptor): ApiHelper {

            return INSTANCE ?: ApiHelper(token, interceptor).apply {
                INSTANCE = this
            }
        }

        @JvmStatic fun destroyInstance() {
            INSTANCE = null
        }
    }

    init {
        val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()

        retrofit = Retrofit
                .Builder()
                .baseUrl(endpoint)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    fun <T> createService(service: Class<T>): T {
        return retrofit.create(service)
    }

}