package com.mhasancse15.cleanmaster

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import om.mhasancse15.cleanmaster.BuildConfig
import timber.log.Timber

@HiltAndroidApp
class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initTimber()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(object : Timber.DebugTree() {
                override fun createStackElementTag(element: StackTraceElement): String {
                    return super.createStackElementTag(element) + ':' + element.lineNumber
                }
            })
        }
    }
}