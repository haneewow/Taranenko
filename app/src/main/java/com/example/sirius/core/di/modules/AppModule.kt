package com.example.sirius.core.di.modules

import android.app.Application
import android.content.Context
import com.example.sirius.core.db.AppDatabase
import com.example.sirius.domain.model.SessionInfo
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