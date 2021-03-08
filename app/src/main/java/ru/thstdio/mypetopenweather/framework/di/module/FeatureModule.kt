package ru.thstdio.mypetopenweather.framework.di.module

import android.content.Context
import com.github.terrakok.cicerone.Router
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.thstdio.core.navigation.AppRouter
import ru.thstdio.core_db.api.data.DbClient
import ru.thstdio.core_db.impl.di.CoreDbComponent
import ru.thstdio.core_network.impl.di.CoreNetworkComponent
import ru.thstdio.feature_cities.api.CitiesFeatureApi
import ru.thstdio.feature_cities.impl.framework.di.CitiesFeatureComponentHolder
import ru.thstdio.feature_cities.impl.framework.di.CitiesFeatureDependencies
import ru.thstdio.mypetopenweather.framework.navigation.AppNavigation
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FeatureModule {

    @Singleton
    @Provides
    fun provideCitiesFeatureDependencies(
        @ApplicationContext applicationContext: Context,
        navigator: AppNavigation
    ): CitiesFeatureDependencies {
        return object : CitiesFeatureDependencies {
            override fun dbClient(): DbClient = CoreDbComponent.get(applicationContext).dbClient()
            override fun httpClient(): Retrofit = CoreNetworkComponent.get().getHttpClient()
            override fun globalNavigator(): AppRouter<Router> = navigator
        }
    }

    @Provides
    fun provideFeatureScanner(dependencies: CitiesFeatureDependencies): CitiesFeatureApi {
        CitiesFeatureComponentHolder.init(dependencies)
        return CitiesFeatureComponentHolder.get()
    }
}