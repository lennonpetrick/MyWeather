package com.test.myweather.data.repository.datasource

import com.test.myweather.data.entities.CityEntity
import io.reactivex.Single

interface WeatherDataSource {

    /**
     * Fetches the current weather for the entry [city]
     *
     * @return A single of [CityEntity]
     * */
    fun getWeather(city: String): Single<CityEntity>

    /**
     * Fetches the current weather for the entry coordinates [lon] [lat]
     *
     * @return A single of [CityEntity]
     * */
    fun getWeather(lon: Double, lat: Double): Single<CityEntity>

}