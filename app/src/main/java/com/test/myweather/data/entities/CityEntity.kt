package com.test.myweather.data.entities

import com.google.gson.annotations.SerializedName

data class CityEntity(
        @SerializedName("coord") var coordinates: CoordEntity? = null,
        @SerializedName("sys") var country: CountryEntity? = null,
        @SerializedName("weather") var weathers: List<WeatherEntity>? = null,
        var name: String? = null,
        var id: Int = 0
)