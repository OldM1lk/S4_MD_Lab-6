package com.example.lab_6.data.local

sealed interface ImageEvent {
    object SaveImageDescription : ImageEvent
    data class SetDescription(val description: String) : ImageEvent
    data class SetImages(val images: List<Image>) : ImageEvent
    data class ShowDialog(val image: Image) : ImageEvent
    object HideDialog : ImageEvent
}