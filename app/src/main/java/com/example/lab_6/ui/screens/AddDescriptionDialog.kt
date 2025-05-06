package com.example.lab_6.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.lab_6.data.local.ImageEvent
import com.example.lab_6.data.local.ImageState

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AddDescriptionDialog(
    state: ImageState,
    onEvent: (ImageEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = {
            onEvent(ImageEvent.HideDialog)
        },
        title = { Text("Добавить описание") },
        text = {
            TextField(
                value = state.description,
                onValueChange = {
                    onEvent(ImageEvent.SetDescription(it))
                },
                placeholder = {
                    Text("Описание")
                }
            )
        },
        confirmButton = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                Button(
                    onClick = {
                        onEvent(ImageEvent.SaveImageDescription)
                    }
                ) {
                    Text("Сохранить")
                }
            }
        }
    )
}