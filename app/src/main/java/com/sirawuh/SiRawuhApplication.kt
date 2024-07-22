package com.sirawuh

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SiRawuhApplication: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
