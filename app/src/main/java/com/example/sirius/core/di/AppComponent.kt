package com.example.sirius.core.di

import android.app.Application
import com.example.sirius.MainApplication
import com.example.sirius.core.di.modules.*
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        PresenterModule::class,
        AppDatabaseModule::class
    ]
)
interface AppComponent {
    @Component.Builder
    interface  Builder{
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: MainApplication)
}