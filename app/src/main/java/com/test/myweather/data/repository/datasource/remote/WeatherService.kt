package com.test.myweather.data.repository.datasource.remote

import com.test.myweather.data.entities.CityEntity
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface WeatherService {

    /**
     * Gets a weather of a specific city from the cloud
     * based on the entry [queries]
     *
     * @return A single of weather
     */
    @GET("weather")
    fun getWeather(@QueryMap queries: Map<String, String>): Single<CityEntity>
}