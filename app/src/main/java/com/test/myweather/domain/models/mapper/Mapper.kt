package com.test.myweather.domain.models.mapper

import com.test.myweather.data.entities.*

internal fun cityEntityToModel(entity: CityEntity): City {
    return City(
            id = entity.id,
            name = entity.name,
            coordinates = coordEntityToModel(entity.coordinates),
            country = countryEntityToModel(entity.country),
            weather = weatherEntityListToModelList(entity.weathers)
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

private fun weatherEntityListToModelList(entities: List<WeatherEntity>?): List<Weather>? {
    if (entities == null || entities.isEmpty())
        return null

    val models = arrayListOf<Weather>()
    for (entity in entities) {
        models.add(weatherEntityToModel(entity))
    }

    return models
}

private fun weatherEntityToModel(entity: WeatherEntity): Weather {
    return Weather(
            id = entity.id,
            main = entity.main,
            description = entity.description,
            icon = entity.icon
    )
}