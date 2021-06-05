package com.example.sirius

import android.app.Application
import com.example.sirius.core.di.DaggerAppComponent

class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        val appComponent = DaggerAppComponent
            .builder()
            .application(this)
            .build()
        appComponent.inject(this)
        instance = this
    }

    companion object {
        lateinit var instance: MainApplication
    }
}