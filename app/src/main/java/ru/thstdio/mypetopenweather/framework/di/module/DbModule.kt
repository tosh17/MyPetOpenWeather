package ru.thstdio.mypetopenweather.framework.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.thstdio.core_db.api.data.DbClient
import ru.thstdio.core_db.impl.di.CoreDbComponent

@Module
@InstallIn(SingletonComponent::class)
object DbModule {
    @Provides
    fun provideDbClient(@ApplicationContext applicationContext: Context): DbClient {
        return CoreDbComponent.get(applicationContext).dbClient()
    }
}