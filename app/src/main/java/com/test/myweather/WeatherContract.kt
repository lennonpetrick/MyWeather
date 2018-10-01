package com.test.myweather

import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes

interface WeatherContract {
    interface View {
        fun setWeatherBackground(
                @DrawableRes drawable: Int,
                @ColorRes colorRes: Int
        )
        fun setWeatherIcon(@DrawableRes idRes: Int?)
        fun setWeatherDescription(description: String?)
        fun setWeatherTemperature(temperature: Int)
        fun setCityName(name: String?)
        fun setDateInfo(
                dayOfWeek: String, month: String, day: Int,
                hour: Int, minute: Int
        )
        fun showError(error: String?)
        fun displayLoading()
        fun dismissLoading()
    }

    interface Presenter {
        fun destroy()
        fun fetchCityWeather(city: String)
        fun fetchCurrentWeather(lon: Double, lat: Double)
        fun refresh()
    }
}