package com.example.lab_6.ui.screens.gallery

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil3.compose.AsyncImage
import com.example.lab_6.data.local.ImageEvent
import com.example.lab_6.data.local.ImageState
import com.example.lab_6.ui.screens.AddDescriptionDialog

@Composable
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
fun GalleryScreen(
    state: ImageState,
    onEvent: (ImageEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    if (state.isAddingDescription) {
        AddDescriptionDialog(
            state = state,
            onEvent = onEvent
        )
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(state.images) { image ->
            AsyncImage(
                model = image.uri.toUri(),
                contentDescription = image.description,
                modifier = Modifier
                    .fillMaxWidth()
                    .combinedClickable(
                        onClick = { },
                        onLongClick = {
                            onEvent(ImageEvent.ShowDialog(image))
                        }
                    ),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}