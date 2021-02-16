package ru.thstdio.mypetopenweather.framework.room

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.thstdio.mypetopenweather.framework.room.dao.PlaceDao
import ru.thstdio.mypetopenweather.framework.room.entity.PlaceDBO

@Database(
    entities = [PlaceDBO::class],
    version = AppDbContract.DATABASE_VERSION, exportSchema = false
)
abstract class AppDb : RoomDatabase() {
    abstract val placeDao: PlaceDao
}

