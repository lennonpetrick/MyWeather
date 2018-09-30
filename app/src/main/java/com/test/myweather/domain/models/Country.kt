package com.test.myweather.data.entities

import java.io.Serializable

data class Country(
        var id: Int = 0,
        var country: String? = null
) : Serializable