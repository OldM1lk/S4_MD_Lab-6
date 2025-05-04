package com.example.lab_6.ui.screens.images

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ImagesScreen(
    modifier: Modifier = Modifier,
    imagesViewModel: ImagesViewModel = viewModel()
) {
    val images by imagesViewModel.images.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        items(images) { image ->
            AsyncImage(
                model = image.contentUri,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                /*.clickable()*/,
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}