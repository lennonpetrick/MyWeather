package com.test.myweather.data.entities

import java.io.Serializable

data class City(
        var coordinates: Coord? = null,
        var country: Country? = null,
        var weather: List<Weather>? = null,
        var name: String? = null,
        var id: Int = 0
) : Serializable