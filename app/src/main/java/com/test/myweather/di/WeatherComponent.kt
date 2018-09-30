package com.test.myweather.di

import com.test.myweather.WeatherActivity
import dagger.Component

@Component(modules = [WeatherModule::class])
@ApplicationScope
interface WeatherComponent {
    fun inject(activity: WeatherActivity)
}