package com.test.myweather.data.repository

import com.test.myweather.data.entities.CityEntity
import com.test.myweather.data.repository.datasource.WeatherDataSource
import com.test.myweather.domain.WeatherRepository
import com.test.myweather.shared.LocalStorage
import com.test.myweather.shared.NoConnectionException
import io.reactivex.Observable

class WeatherRepositoryImpl private constructor(
        private val cloudDataSource: WeatherDataSource,
        private val localDataSource: WeatherDataSource
) : WeatherRepository {

    private var refreshObservable: Observable<CityEntity>? = null

    companion object {
        private var INSTANCE: WeatherRepositoryImpl? = null

        @JvmStatic fun getInstance(
                cloudDataSource: WeatherDataSource,
                localDataSource: WeatherDataSource
        ): WeatherRepositoryImpl {

            return INSTANCE
                    ?: WeatherRepositoryImpl(cloudDataSource, localDataSource)
                    .apply {
                        INSTANCE = this
                    }
        }

        @JvmStatic fun destroyInstance() {
            INSTANCE = null
        }
    }

    override fun refresh(): Observable<CityEntity> {
        if (refreshObservable != null) {
            return refreshObservable!!
        }

        val local = localDataSource as LocalStorage
        if (local.isCached()) {
            return localDataSource.getWeather("").toObservable()
        }

        return Observable.empty()
    }

    override fun getWeather(city: String): Observable<CityEntity> {
        val local = localDataSource as LocalStorage
        val remoteData = cloudDataSource.getWeather(city)
                .map {
                    local.save(it)
                    it
                }
                .toObservable()

        if (!local.isCached()) {
            return remoteData.also { cacheLastCall(it) }
        }

        return Observable.concat(
                localDataSource.getWeather(city).toObservable(),
                remoteData.onErrorResumeNext { error: Throwable ->
                    if (error is NoConnectionException) {
                        Observable.empty()
                    } else {
                        Observable.error(error)
                    }
                }).also { cacheLastCall(it) }
    }

    override fun getWeather(lon: Double, lat: Double): Observable<CityEntity> {
        val local = localDataSource as LocalStorage
        val remoteData = cloudDataSource.getWeather(lon, lat)
                .map {
                    local.save(it)
                    it
                }
                .toObservable()

        if (!local.isCached()) {
            return remoteData.also { cacheLastCall(it) }
        }

        return Observable.concat(
                localDataSource.getWeather(lon, lat).toObservable(),
                remoteData.onErrorResumeNext { error: Throwable ->
                    if (error is NoConnectionException) {
                        Observable.empty()
                    } else {
                        Observable.error(error)
                    }
                }).also { cacheLastCall(it) }
    }

    private fun cacheLastCall(observable: Observable<CityEntity>) {
        refreshObservable = observable
    }
}