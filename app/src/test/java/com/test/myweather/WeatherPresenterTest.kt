package com.test.myweather

import com.google.gson.Gson
import com.test.myweather.domain.models.City
import com.test.myweather.domain.usecase.WeatherUseCase
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class WeatherPresenterTest {

    private lateinit var presenter: WeatherContract.Presenter
    private lateinit var city: City

    @Mock private lateinit var view: WeatherContract.View
    @Mock private lateinit var useCase: WeatherUseCase

    private val json = "{\n" +
            "  \"coord\": {\n" +
            "    \"lon\": -89.24,\n" +
            "    \"lat\": 43.45\n" +
            "  },\n" +
            "  \"weather\": {\n" +
            "    \"id\": 803,\n" +
            "    \"main\": \"Clouds\",\n" +
            "    \"description\": \"broken clouds\",\n" +
            "    \"icon\": \"04d\"\n" +
            "  },\n" +
            "  \"main\": {\n" +
            "    \"temp\": 289.48,\n" +
            "    \"pressure\": 1018,\n" +
            "    \"humidity\": 41,\n" +
            "    \"temp_min\": 289.15,\n" +
            "    \"temp_max\": 290.15\n" +
            "  },\n" +
            "  \"sys\": {\n" +
            "    \"id\": 2997,\n" +
            "    \"country\": \"US\"\n" +
            "  },\n" +
            "  \"id\": 5268977,\n" +
            "  \"name\": \"Rio\"\n" +
            "}"

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        setUpSchedulers()

        city = Gson().fromJson(json, City::class.java)
        presenter = WeatherPresenter(view, useCase)
    }

    @After
    fun tearDown() {
        resetSchedulers()
    }

    @Test
    fun fetchCityWeather() {
        `when`(useCase.getWeather(anyString())).thenReturn(Observable.just(city))

        presenter.fetchCityWeather(anyString())

        verify(view).displayLoading()
        verify(view).dismissLoading()
        verify(view).setWeatherTemperature(anyInt())
        verify(view).setWeatherBackground(anyInt(), anyInt())
        verify(view).setWeatherDescription(anyString())
        verify(view).setWeatherIcon(anyInt())
        verify(view).setCityName(anyString())
        verify(view).setDateInfo(anyString(), anyString(), anyInt(), anyInt(), anyInt())
        verify(view, never()).showError(anyString())
    }

    @Test
    fun fetchCurrentWeather() {
        `when`(useCase.getWeather(anyDouble(), anyDouble())).thenReturn(Observable.just(city))

        presenter.fetchCurrentWeather(anyDouble(), anyDouble())

        verify(view).displayLoading()
        verify(view).dismissLoading()
        verify(view).setWeatherTemperature(anyInt())
        verify(view).setWeatherBackground(anyInt(), anyInt())
        verify(view).setWeatherDescription(anyString())
        verify(view).setWeatherIcon(anyInt())
        verify(view).setCityName(anyString())
        verify(view).setDateInfo(anyString(), anyString(), anyInt(), anyInt(), anyInt())
        verify(view, never()).showError(anyString())
    }

    @Test
    fun refresh() {
        `when`(useCase.getWeather(anyString())).thenReturn(Observable.just(city.apply {
            this.name = "first"
        }))

        presenter.fetchCityWeather(anyString())
        verify(view).setCityName("first")

        `when`(useCase.getWeather(anyDouble(), anyDouble())).thenReturn(Observable.just(city.apply {
            this.name = "second"
        }))

        presenter.fetchCurrentWeather(anyDouble(), anyDouble())
        presenter.refresh()
        verify(view, times(2)).setCityName("second")
    }

    private fun resetSchedulers() {
        RxJavaPlugins.reset()
        RxAndroidPlugins.reset()
    }

    private fun setUpSchedulers() {
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }
}