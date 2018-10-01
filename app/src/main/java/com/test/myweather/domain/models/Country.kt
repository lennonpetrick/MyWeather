package com.test.myweather.domain.models

import java.io.Serializable

data class Country(
        var id: Int = 0,
        var country: String? = null
) : Serializable