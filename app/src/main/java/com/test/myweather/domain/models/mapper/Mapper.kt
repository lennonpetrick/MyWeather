package com.test.myweather.domain.models.mapper

import com.test.myweather.data.entities.*
import com.test.myweather.domain.models.*

internal fun cityEntityToModel(entity: CityEntity): City {
    return City(
            id = entity.id,
            name = entity.name,
            coordinates = coordEntityToModel(entity.coordinates),
            country = countryEntityToModel(entity.country),
            weather = weatherEntityListToModelList(entity.weathers),
            main = mainEntityToModel(entity.main)
    )
}

private fun countryEntityToModel(entity: CountryEntity?): Country? {
    if (entity == null)
        return null

    return Country(
            id = entity.id,
            country = entity.country
    )
}

private fun coordEntityToModel(entity: CoordEntity?): Coord? {
    if (entity == null)
        return null

    return Coord(
            lon = entity.lon,
            lat = entity.lat
    )
}

private fun mainEntityToModel(entity: MainEntity?): Main? {
    if (entity == null)
        return null

    return Main(
            temperature = entity.temperature,
            tempMin = entity.tempMin,
            tempMax = entity.tempMax,
            humidity = entity.humidity,
            pressure = entity.pressure
    )
}

private fun weatherEntityListToModelList(entities: List<WeatherEntity>?): Weather? {
    if (entities == null || entities.isEmpty())
        return null

    return weatherEntityToModel(entities[0]) // Getting just the primary one
}

private fun weatherEntityToModel(entity: WeatherEntity): Weather {
    return Weather(
            id = entity.id,
            main = entity.main,
            description = entity.description,
            icon = entity.icon
    )
}