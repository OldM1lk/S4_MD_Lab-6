package com.example.lab_6.ui.screens.gallery

import android.app.Application
import android.content.ContentUris
import android.provider.MediaStore
import androidx.core.net.toUri
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

    fun onEvent(event: ImageEvent) {
        when (event) {
            ImageEvent.HideDialog -> {
                _state.update {
                    it.copy(
                        isAddingDescription = false,
                        description = ""
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
                viewModelScope.launch(Dispatchers.IO) {
                    dao.upsertImage(image)
                }
                _state.update {
                    it.copy(
                        isAddingDescription = false,
                        description = ""
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

            is ImageEvent.SetImages -> {
                _state.update {
                    it.copy(
                        images = event.images
                    )
                }
            }

            is ImageEvent.ShowDialog -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val image = dao.getImageById(event.image.id)
                    val description = image?.description ?: ""

                    _state.update {
                        it.copy(
                            isAddingDescription = true,
                            id = event.image.id,
                            uri = event.image.uri.toUri(),
                            description = description
                        )
                    }
                }
            }
        }

    }

    fun loadImages() {
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

            _state.update {
                it.copy(
                    images = photos
                )
            }
        }
    }
}