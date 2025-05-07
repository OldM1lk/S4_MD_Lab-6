package com.example.lab_6.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class Image(
    @PrimaryKey
    val id: Long,
    val description: String? = null,
    val uri: String
)