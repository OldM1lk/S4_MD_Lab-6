package com.example.lab_6.data.local

import android.net.Uri

sealed interface ImageEvent {
    object SaveImageDescription: ImageEvent
    data class SetId(val id: Long): ImageEvent
    data class SetDescription(val description: String): ImageEvent
    data class SetUri(val uri: Uri): ImageEvent
    object ShowDialog: ImageEvent
    object HideDialog: ImageEvent
}