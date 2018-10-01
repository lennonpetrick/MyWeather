package com.test.myweather.domain.usecase

import com.test.myweather.domain.models.City
import com.test.myweather.domain.WeatherRepository
import com.test.myweather.domain.models.mapper.cityEntityToModel
import io.reactivex.Observable

class WeatherUseCaseImpl(private val repository: WeatherRepository) : WeatherUseCase {

    override fun getWeather(city: String): Observable<City> {
        return repository.getWeather(city)
                .map { cityEntityToModel(it) }
    }

    override fun getWeather(lon: Double, lat: Double): Observable<City> {
        return repository.getWeather(lon, lat)
                .map { cityEntityToModel(it) }
    }
}