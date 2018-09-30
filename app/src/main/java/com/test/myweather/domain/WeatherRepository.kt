package com.test.myweather.domain

import com.test.myweather.data.entities.CityEntity
import io.reactivex.Observable

interface WeatherRepository {

    /**
     * Fetches the current weather from a resource for the entry [city]
     *
     * @return A observable of [CityEntity]
     * */
    fun getWeather(city: String): Observable<CityEntity>

    /**
     * Fetches the current weather from a resource for the entry coordinates [lon] [lat]
     *
     * @return A observable of [CityEntity]
     * */
    fun getWeather(lon: Double, lat: Double): Observable<CityEntity>

}