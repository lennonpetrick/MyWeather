package com.test.myweather.domain

import com.test.myweather.data.entities.CityEntity
import io.reactivex.Observable

interface WeatherRepository {

    /**
     * Returns the last call if it's not null,
     * otherwise returns the last data saved into local
     *
     * @return The last observable of [CityEntity]
     * */
    fun refresh(): Observable<CityEntity>

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