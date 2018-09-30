package com.test.myweather.data.entities

import java.io.Serializable

data class Coord(
        var lon: Double = 0.0,
        var lat: Double = 0.0
) : Serializable