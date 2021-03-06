package ru.thstdio.core_db.impl.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.thstdio.core_db.api.data.DbClient
import ru.thstdio.core_db.impl.data.DbClientImpl
import ru.thstdio.mypetopenweather.framework.room.AppDb
import ru.thstdio.mypetopenweather.framework.room.DATABASE_NAME
import javax.inject.Singleton

@Module
internal abstract class DbModule {

    @Singleton
    @Binds
    abstract fun provideDbClientApi(dbClientImpl: DbClientImpl): DbClient

    companion object {
        @Singleton
        @Provides
        fun provideAppDb(applicationContext: Context): AppDb {
            return Room.databaseBuilder(
                applicationContext,
                AppDb::class.java, DATABASE_NAME
            ).setJournalMode(RoomDatabase.JournalMode.AUTOMATIC)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}