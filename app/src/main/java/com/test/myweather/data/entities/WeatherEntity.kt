package com.test.myweather.data.entities

data class WeatherEntity(
        var id: Int = 0,
        var main: String? = null,
        var description: String? = null,
        var icon: String? = null
)