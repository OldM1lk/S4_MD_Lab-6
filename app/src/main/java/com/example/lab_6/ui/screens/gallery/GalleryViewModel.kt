package com.example.lab_6.ui.screens.gallery

import android.app.Application
import android.content.ContentUris
import android.provider.MediaStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab_6.data.local.Image
import com.example.lab_6.data.local.ImageEvent
import com.example.lab_6.data.local.ImageState
import com.example.lab_6.data.sdk29AndUp
import com.example.lab_6.db.ImageDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GalleryViewModel(
    application: Application,
    private val dao: ImageDao
) : AndroidViewModel(application) {
    private val _state = MutableStateFlow(ImageState())
    val state = _state.asStateFlow()

    private val _images = MutableStateFlow<List<Image>>(emptyList())

    fun onEvent(event: ImageEvent) {
        when (event) {
            ImageEvent.HideDialog -> {
                _state.update {
                    it.copy(
                        isAddingDescription = false
                    )
                }
            }

            ImageEvent.SaveImageDescription -> {
                val id = state.value.id
                val description = state.value.description
                val uri = state.value.uri.toString()

                if (description.isBlank()) {
                    return
                }

                val image = Image(
                    id = id,
                    description = description,
                    uri = uri
                )
                viewModelScope.launch {
                    dao.upsertImage(image)
                }
                _state.update {
                    it.copy(
                        isAddingDescription = false,
                        description = ""
                    )
                }
            }

            is ImageEvent.SetId -> {
                _state.update {
                    it.copy(
                        id = event.id
                    )
                }
            }

            is ImageEvent.SetDescription -> {
                _state.update {
                    it.copy(
                        description = event.description
                    )
                }
            }

            is ImageEvent.SetUri -> {
                _state.update {
                    it.copy(
                        uri = event.uri
                    )
                }
            }

            is ImageEvent.SetImages -> {
                _state.update {
                    it.copy(
                        images = event.images
                    )
                }
            }

            ImageEvent.ShowDialog -> {
                _state.update {
                    it.copy(
                        isAddingDescription = true
                    )
                }
            }
        }

    }

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

            val photos = mutableListOf<Image>()

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
                    val uri = ContentUris.withAppendedId(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        id
                    )
                    photos.add(
                        Image(
                            id = id,
                            uri = uri.toString()
                        )
                    )
                }
            }
            _images.value = photos

            _state.update {
                it.copy(
                    images = photos
                )
            }
        }
    }
}