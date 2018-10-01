package com.test.myweather.domain.models

import java.io.Serializable

data class Coord(
        var lon: Double = 0.0,
        var lat: Double = 0.0
) : Serializable