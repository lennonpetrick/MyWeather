package com.test.myweather.domain.usecase

import com.test.myweather.data.entities.City
import io.reactivex.Observable

interface WeatherUseCase {

    /**
     * Fetches the current weather from the repository for the entry [city]
     *
     * @return A observable of [City]
     * */
    fun getWeather(city: String): Observable<City>

    /**
     * Fetches the current weather from the repository for the entry coordinates [lon] [lat]
     *
     * @return A observable of [City]
     * */
    fun getWeather(lon: Double, lat: Double): Observable<City>

}