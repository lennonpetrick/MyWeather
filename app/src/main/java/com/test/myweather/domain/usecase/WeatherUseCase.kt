package com.test.myweather.domain.usecase

import com.test.myweather.domain.models.City
import io.reactivex.Observable

interface WeatherUseCase {

    /**
     * Returns the last call if it's not null,
     * otherwise returns the last data saved into local
     *
     * @return The last observable of [City]
     * */
    fun refresh(): Observable<City>

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