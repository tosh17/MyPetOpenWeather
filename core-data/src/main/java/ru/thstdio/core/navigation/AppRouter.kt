package ru.thstdio.core.navigation

interface AppRouter {
    fun openFeature(feature: Features)
    fun onBack()
}