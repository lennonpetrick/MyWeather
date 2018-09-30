package com.test.myweather.shared

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiHelper private constructor(val token: String) {

    private val endpoint = "https://api.openweathermap.org/data/2.5/"
    private val retrofit: Retrofit

    companion object {
        private var INSTANCE: ApiHelper? = null

        @JvmStatic
        @Synchronized
        fun getInstance(token: String): ApiHelper {
            return INSTANCE ?: ApiHelper(token).apply {
                INSTANCE = this
            }
        }

        @JvmStatic fun destroyInstance() {
            INSTANCE = null
        }
    }

    init {
        retrofit = Retrofit
                .Builder()
                .baseUrl(endpoint)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    fun <T> createService(service: Class<T>): T {
        return retrofit.create(service)
    }

}