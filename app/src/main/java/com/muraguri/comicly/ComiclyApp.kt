package com.muraguri.comicly

import android.app.Application
import com.muraguri.comicly.di.AppDataContainer
import com.muraguri.comicly.di.DefaultAppDataContainer
import timber.log.Timber


class ComiclyApp : Application() {

    lateinit var container : AppDataContainer
    override fun onCreate() {
        super.onCreate()
        plantDebugBuildLogger()
        container = DefaultAppDataContainer(this)
    }

    private fun plantDebugBuildLogger() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}