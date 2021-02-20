package ru.thstdio.mypetopenweather.framework.di.module

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.thstdio.mypetopenweather.framework.room.AppDb
import ru.thstdio.mypetopenweather.framework.room.DATABASE_NAME
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DBModule {

    @Singleton
    @Provides
    fun providerBd(@ApplicationContext applicationContext: Context): AppDb {
        return Room.databaseBuilder(
            applicationContext,
            AppDb::class.java, DATABASE_NAME
        ).setJournalMode(RoomDatabase.JournalMode.AUTOMATIC)
            //.fallbackToDestructiveMigration()
            .build()
    }


}