package com.example.gallery.core.di.modules

import android.app.Application
import androidx.room.Room
import com.example.gallery.core.db.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppDatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(app: Application): AppDatabase = Room
        .databaseBuilder(app, AppDatabase::class.java, AppDatabase.DB_NAME)
        .build()

    @Provides
    @Singleton
    fun provideDao(db: AppDatabase) = db.appDao()
}