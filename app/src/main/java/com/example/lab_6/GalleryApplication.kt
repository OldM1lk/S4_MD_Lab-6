package com.example.lab_6

import android.app.Application
import com.example.lab_6.di.databaseModule
import com.example.lab_6.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GalleryApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@GalleryApplication)
            modules(databaseModule, viewModelModule)
        }
    }
}