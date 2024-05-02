package com.valance.medicine

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager


class Medicine : Application() {
    companion object {
        lateinit var sharedPreferences: SharedPreferences
    }
    override fun onCreate() {
        super.onCreate()
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

    }
}
