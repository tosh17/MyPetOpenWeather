package ru.thstdio.core.navigation

interface AppRouter<T> {
    fun openFeature(feature: Features)
    fun onBack()
    fun getRouter(): T
}