package com.steingolditay.app.moviesapp

import android.app.Application
import com.preference.PowerPreference
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Application: Application(){
    override fun onCreate() {
        super.onCreate()
        PowerPreference.init(this);

    }
}