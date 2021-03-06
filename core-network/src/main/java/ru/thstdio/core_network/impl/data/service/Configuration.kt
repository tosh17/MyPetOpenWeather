package ru.thstdio.core_network.impl.data.service

data class Configuration(val appId: String, val units: UnitsType, val language: Language) {


    enum class Language(val value: String) {
        Russian("ru"), English("en")
    }

    enum class UnitsType(val value: String) {
        Standard("standard"), Metric("metric"), Imperial("imperial")
    }

}