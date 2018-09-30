package com.test.myweather.domain.models.mapper

import com.google.gson.Gson
import com.test.myweather.data.entities.CityEntity
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Test

class MapperTest {

    val json = "{\n" +
            "    \"coord\": {\n" +
            "        \"lon\": 16.37,\n" +
            "        \"lat\": 48.21\n" +
            "    },\n" +
            "    \"weather\": [\n" +
            "        {\n" +
            "            \"id\": 800,\n" +
            "            \"main\": \"Clear\",\n" +
            "            \"description\": \"clear sky\",\n" +
            "            \"icon\": \"01d\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"sys\": {\n" +
            "        \"id\": 5934,\n" +
            "        \"country\": \"AT\"\n" +
            "    },\n" +
            "    \"id\": 2761369,\n" +
            "    \"name\": \"Vienna\"\n" +
            "}"

    @Test
    fun cityEntityToModel() {
        val entity = Gson().fromJson(json, CityEntity::class.java)
        val model = cityEntityToModel(entity)

        assertThat(model.id, `is`(entity.id))
        assertThat(model.name, `is`(entity.name))

        assertThat(model.coordinates!!.lon, `is`(entity.coordinates!!.lon))
        assertThat(model.coordinates!!.lat, `is`(entity.coordinates!!.lat))

        assertThat(model.country!!.id, `is`(entity.country!!.id))
        assertThat(model.country!!.country, `is`(entity.country!!.country))

        assertThat(model.weather!![0].id, `is`(entity.weathers!![0].id))
        assertThat(model.weather!![0].main, `is`(entity.weathers!![0].main))
        assertThat(model.weather!![0].description, `is`(entity.weathers!![0].description))
        assertThat(model.weather!![0].icon, `is`(entity.weathers!![0].icon))
    }
}