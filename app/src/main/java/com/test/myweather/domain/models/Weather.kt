package com.test.myweather.domain.models

import java.io.Serializable

data class Weather(
        var id: Int = 0,
        var main: String? = null,
        var description: String? = null,
        var icon: String? = null
) : Serializable