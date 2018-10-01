package com.test.myweather.shared

import com.test.myweather.R

class WeatherThemeHelper private constructor() {

    private var iconsMap: MutableMap<String, Int>? = null

    companion object {
        private var INSTANCE: WeatherThemeHelper? = null

        @JvmStatic
        fun getInstance(): WeatherThemeHelper {

            return INSTANCE ?: WeatherThemeHelper().apply {
                INSTANCE = this
            }
        }

        @JvmStatic fun destroyInstance() {
            INSTANCE = null
        }
    }

    init {
        iconsMap = HashMap()
        iconsMap!!.apply {
            this["01d"] = R.drawable.clear_day
            this["01n"] = R.drawable.clear_night

            this["03d"] = R.drawable.scattered_clouds
            this["03n"] = R.drawable.scattered_clouds
            this["02d"] = R.drawable.clouds_day
            this["04d"] = R.drawable.clouds_day
            this["02n"] = R.drawable.clouds_night
            this["04n"] = R.drawable.clouds_night

            this["10d"] = R.drawable.drizzle_day
            this["10n"] = R.drawable.drizzle_night
            this["09d"] = R.drawable.showers_day
            this["09n"] = R.drawable.showers_night

            this["13d"] = R.drawable.snow_scattered_day
            this["13n"] = R.drawable.snow_scattered_night

            this["11d"] = R.drawable.storm_day
            this["11n"] = R.drawable.storm_night

            //50d
            this["741"] = R.drawable.fog
            this["731"] = R.drawable.wind
            this["701"] = R.drawable.mist
            this["721"] = R.drawable.haze
        }
    }

    fun getIcon(key: String?): Int? {
        return iconsMap!![key]
    }

    fun getBackground(key: String?): Int {
        return when(key) {
            "01d" -> R.drawable.day_background
            "01n" -> R.drawable.night_background
            else -> R.drawable.dark_background
        }
    }

    fun getBackgroundColor(key: String?): Int {
        return when(key) {
            "01d" -> R.color.background_day_start
            "01n" -> R.color.background_night_start
            else -> R.color.background_dark_start
        }
    }

}