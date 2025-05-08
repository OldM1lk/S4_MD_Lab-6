package com.example.lab_6.di

import android.app.Application
import androidx.room.Room
import com.example.lab_6.db.ImageDao
import com.example.lab_6.db.ImageDatabase
import org.koin.dsl.module

fun provideDatabase(application: Application): ImageDatabase =
    Room.databaseBuilder(
        application,
        ImageDatabase::class.java,
        "images.db"
    ).fallbackToDestructiveMigration(false).build()

fun provideDao(imageDatabase: ImageDatabase): ImageDao = imageDatabase.dao

val databaseModule = module {
    single { provideDatabase(get()) }
    single { provideDao(get()) }
}