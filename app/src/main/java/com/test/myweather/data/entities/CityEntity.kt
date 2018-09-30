package com.test.myweather.data.entities

import com.google.gson.annotations.SerializedName

data class CityEntity(
        @SerializedName("coord") var coordinates: CoordEntity? = null,
        @SerializedName("sys") var country: CountryEntity? = null,
        var weather: List<Weather>? = null,
        var name: String? = null,
        var id: Int = 0
)