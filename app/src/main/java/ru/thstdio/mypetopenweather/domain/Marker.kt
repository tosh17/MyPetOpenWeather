package ru.thstdio.mypetopenweather.domain

data class Marker(val location: Location, var title: String,val snippet:String,val idWeatherIcon:Int) {
}