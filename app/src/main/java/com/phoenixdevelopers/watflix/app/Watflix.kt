package com.phoenixdevelopers.watflix.app

import android.app.Application
import com.phoenixdevelopers.watflix.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class Watflix : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {

            Timber.plant(Timber.DebugTree())

        }

    }

}