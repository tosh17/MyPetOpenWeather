package ru.thstdio.mypetopenweather.framework.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.thstdio.core_network.impl.di.CoreNetworkComponent

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    fun provideNetworkClient(): Retrofit {
        return CoreNetworkComponent.get().getHttpClient()
    }
}