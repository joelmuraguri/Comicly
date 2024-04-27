package com.muraguri.comicly

import android.app.Application
import timber.log.Timber


class ComiclyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        plantDebugBuildLogger()
    }

    private fun plantDebugBuildLogger() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}