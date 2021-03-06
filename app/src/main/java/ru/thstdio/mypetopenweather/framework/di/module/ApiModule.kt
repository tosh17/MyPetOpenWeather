package ru.thstdio.mypetopenweather.framework.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.thstdio.core_network.impl.di.CoreNetworkComponent
import ru.thstdio.mypetopenweather.framework.api.service.OpenWeatherApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    fun provideDbClient(@ApplicationContext applicationContext: Context): Retrofit {
        return CoreNetworkComponent.get().getHttpClient()
    }

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): OpenWeatherApi {
        return retrofit.create(OpenWeatherApi::class.java)
    }
}