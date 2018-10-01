package com.test.myweather.data.entities

import com.google.gson.annotations.SerializedName

data class MainEntity(
        @SerializedName("temp") var temperature: Double = 0.0,
        @SerializedName("temp_min") var tempMin: Double = 0.0,
        @SerializedName("temp_max") var tempMax: Double = 0.0,
        var pressure: Double = 0.0,
        var humidity: Double = 0.0
)