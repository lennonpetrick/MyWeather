package com.test.myweather.domain.models

import java.io.Serializable

data class City(
        var coordinates: Coord? = null,
        var country: Country? = null,
        var weather: Weather? = null,
        val main: Main? = null,
        var name: String? = null,
        var id: Int = 0
) : Serializable