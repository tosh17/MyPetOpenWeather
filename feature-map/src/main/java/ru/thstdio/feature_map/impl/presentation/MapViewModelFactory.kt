package ru.thstdio.feature_map.impl.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.thstdio.core.navigation.AppRouter
import ru.thstdio.module_injector.di.PerFeature
import javax.inject.Inject


@PerFeature
class MapViewModelFactory @Inject constructor(
    private val useCase: MapUseCase,
    private val navigation: AppRouter
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when (modelClass) {
            MapViewModel::class.java -> MapViewModel(useCase, navigation)
            else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
        } as T

    }

}