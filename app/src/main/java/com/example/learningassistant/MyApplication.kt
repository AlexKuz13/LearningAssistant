package com.example.learningassistant

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.asLiveData
import com.example.learningassistant.data.DataStoreRepository
import com.example.learningassistant.utilits.restartActivity
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class MyApplication : Application() {

    lateinit var dataStoreRepository: DataStoreRepository

    override fun onCreate() {
        super.onCreate()

        dataStoreRepository = DataStoreRepository(this)

        dataStoreRepository.readDarkTheme.asLiveData().observeForever() { darkTheme ->
            if (darkTheme != null) {
                if (darkTheme) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }
            restartActivity()
        }
    }


}