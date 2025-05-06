package com.example.lab_6.data.local

import android.net.Uri

data class ImageState(
    val id: Long = 0,
    val description: String = "",
    val uri: Uri = Uri.EMPTY,
    val images: List<Image> = emptyList(),
    val isAddingDescription: Boolean = false
)