package com.test.myweather.data.repository.datasource.remote

import com.test.myweather.data.entities.CityEntity
import com.test.myweather.data.repository.datasource.WeatherDataSource
import com.test.myweather.shared.ApiHelper
import io.reactivex.Single
import java.util.*

class CloudWeatherDataSource(private val helper: ApiHelper) : WeatherDataSource {

    private val service = helper.createService(WeatherService::class.java)

    override fun getWeather(city: String): Single<CityEntity> {
        return service.getWeather(
                createQuery().apply {
                    this["q"] = city
                }
        )
    }

    override fun getWeather(lon: Double, lat: Double): Single<CityEntity> {
        return service.getWeather(
                createQuery().apply {
                    this["lon"] = lon.toString()
                    this["lat"] = lat.toString()
                }
        )
    }

    private fun createQuery(): MutableMap<String, String> {
        return HashMap<String, String>().apply {
            this["APPID"] = helper.token
            this["units"] = "metric"
        }
    }
}