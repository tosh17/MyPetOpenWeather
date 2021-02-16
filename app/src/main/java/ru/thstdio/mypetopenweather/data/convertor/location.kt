package ru.thstdio.mypetopenweather.data.convertor

//Todo как правильней тут классы имеют одинаковое имя классы переименовать?
fun android.location.Location.toDomainLocation(): ru.thstdio.mypetopenweather.domain.Location {
    return ru.thstdio.mypetopenweather.domain.Location(
        longitude = this.longitude,
        latitude = this.latitude
    )
}