package com.example.sirius.core.di

import android.app.Application
import com.example.sirius.MainApplication
import com.example.sirius.core.di.modules.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        PresenterModule::class,
        AppDatabaseModule::class,
        FragmentModule::class,
        DeveloperNotesModule::class,
        HttpModule::class
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