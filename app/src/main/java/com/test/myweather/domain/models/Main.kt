package com.test.myweather.domain.models

import java.io.Serializable

data class Main(
        var temperature: Double = 0.0,
        var tempMin: Double = 0.0,
        var tempMax: Double = 0.0,
        var pressure: Double = 0.0,
        var humidity: Double = 0.0
) : Serializable