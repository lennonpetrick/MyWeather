package com.test.myweather.data.repository.datasource.local

import android.content.SharedPreferences
import com.google.gson.Gson
import com.test.myweather.data.entities.CityEntity
import com.test.myweather.data.repository.datasource.WeatherDataSource
import com.test.myweather.shared.LocalStorage
import io.reactivex.Single

class LocalWeatherDataSource(
        private val sharedPreferences: SharedPreferences
) : WeatherDataSource, LocalStorage {

    private val sharedPreferencesKey = CityEntity::class.java.simpleName

    private var gson: Gson? = null
        get() {
            if (field == null) {
                field = Gson()
            }

            return field
        }

    override fun getWeather(lon: Double, lat: Double): Single<CityEntity> {
        return Single.create { emitter ->
            emitter.onSuccess(jsonToEntity(retrieveData(sharedPreferencesKey)!!))
        }
    }

    override fun getWeather(city: String): Single<CityEntity> {
        return Single.create { emitter ->
            emitter.onSuccess(jsonToEntity(retrieveData(sharedPreferencesKey)!!))
        }
    }

    override fun isCached(): Boolean {
        return sharedPreferences.contains(sharedPreferencesKey)
    }

    override fun <T> save(obj: T) {
        saveData(sharedPreferencesKey, entityToJson(obj as CityEntity))
    }

    private fun jsonToEntity(json: String): CityEntity {
        return gson!!.fromJson(json, CityEntity::class.java)
    }

    private fun entityToJson(entity: CityEntity): String {
        return gson!!.toJson(entity)
    }

    private fun retrieveData(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    private fun saveData(key: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }
}