package com.example.lab_6.ui.screens.images

import android.app.Application
import android.content.ContentUris
import android.provider.MediaStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab_6.data.SharedStoragePhoto
import com.example.lab_6.data.sdk29AndUp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ImagesViewModel(application: Application) : AndroidViewModel(application) {
    private val _images = MutableStateFlow<List<SharedStoragePhoto>>(emptyList())
    val images = _images.asStateFlow()

    init {
        loadImages()
    }

    private fun loadImages() {
        viewModelScope.launch(Dispatchers.IO) {
            val collection = sdk29AndUp {
                MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
            } ?: MediaStore.Images.Media.EXTERNAL_CONTENT_URI

            val projection = arrayOf(
                MediaStore.Images.Media._ID
            )

            val photos = mutableListOf<SharedStoragePhoto>()

            val context = getApplication<Application>().applicationContext
            val contentResolver = context.contentResolver

            contentResolver.query(
                collection,
                projection,
                null,
                null,
                null
            )?.use { cursor ->
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)

                while (cursor.moveToNext()) {
                    val id = cursor.getLong(idColumn)
                    val contentUri = ContentUris.withAppendedId(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        id
                    )
                    photos.add(SharedStoragePhoto(id, contentUri))
                }
            }
            _images.value = photos
        }
    }
}