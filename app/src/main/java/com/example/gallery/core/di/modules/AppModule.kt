package com.example.gallery.core.di.modules

import android.app.Application
import android.content.Context
import com.example.gallery.core.db.AppDatabase
import com.example.gallery.domain.model.SessionInfo
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    @Singleton
    fun provideContext(app: Application): Context = app.baseContext

    @Provides
    @Singleton
    fun provideNumberPrefs(context: Context, appDatabase: AppDatabase) = SessionInfo(context, appDatabase)
}