package com.example.lab_6.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.lab_6.data.local.Image

@Dao
interface ImageDao {
    @Upsert
    suspend fun upsertImage(image: Image)

    @Query("SELECT * FROM image WHERE id = :id")
    suspend fun getImageById(id: Long): Image?
}