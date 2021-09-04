package com.example.gallery.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.gallery.core.db.AppDatabase.Companion.DB_V1
import com.example.gallery.domain.model.data.DeveloperNote

@[Database(
    version = DB_V1, exportSchema = false,
    entities = [
        DeveloperNote::class
    ]
)
TypeConverters(EnumConverter::class)]
abstract class AppDatabase : RoomDatabase() {

    abstract fun appDao(): AppDao

    companion object {
        const val DB_NAME = "app_database"
        const val DB_V1 = 1
    }
}