package com.example.lab_6.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.lab_6.data.local.Image

@Database(
    entities = [Image::class],
    version = 1
)
abstract class ImageDatabase : RoomDatabase() {
    abstract val dao: ImageDao
}