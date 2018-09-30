package com.test.myweather.data.repository

import com.test.myweather.data.repository.datasource.local.LocalWeatherDataSource
import com.test.myweather.data.repository.datasource.remote.CloudWeatherDataSource
import com.test.myweather.data.entities.CityEntity
import com.test.myweather.data.repository.WeatherRepositoryImpl
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyDouble
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class WeatherRepositoryTest {

    private lateinit var repository: WeatherRepositoryImpl

    @Mock
    private lateinit var cloudDataSource: CloudWeatherDataSource
    @Mock
    private lateinit var localDataSource: LocalWeatherDataSource

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        val entity = CityEntity()

        `when`(cloudDataSource.getWeather(anyString())).thenReturn(Single.just(entity))
        `when`(cloudDataSource.getWeather(anyDouble(), anyDouble()))
                .thenReturn(Single.just(entity))

        `when`(localDataSource.getWeather(anyString())).thenReturn(Single.just(entity))
        `when`(localDataSource.getWeather(anyDouble(), anyDouble()))
                .thenReturn(Single.just(entity))

        repository = WeatherRepositoryImpl.getInstance(cloudDataSource, localDataSource)
    }

    @After
    fun tearDown() {
        WeatherRepositoryImpl.destroyInstance()
    }

    @Test
    fun `get weather from remote by city`() {
        `when`(localDataSource.isCached()).thenReturn(false)

        repository.getWeather(anyString())
                .test()
                .assertValueCount(1)

        verify(localDataSource, never()).getWeather(anyString())
    }

    @Test
    fun `get weather from local and remote by city`() {
        `when`(localDataSource.isCached()).thenReturn(true)

        repository.getWeather(anyString())
                .test()
                .assertValueCount(2)

        verify(localDataSource).getWeather(anyString())
    }

    @Test
    fun `get weather from remote by coordinates`() {
        `when`(localDataSource.isCached()).thenReturn(false)

        repository.getWeather(anyDouble(), anyDouble())
                .test()
                .assertValueCount(1)

        verify(localDataSource, never()).getWeather(anyDouble(), anyDouble())
    }

    @Test
    fun `get weather from local and remote by coordinates`() {
        `when`(localDataSource.isCached()).thenReturn(true)

        repository.getWeather(anyDouble(), anyDouble())
                .test()
                .assertValueCount(2)

        verify(localDataSource).getWeather(anyDouble(), anyDouble())
    }
}