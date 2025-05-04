package com.example.lab_6

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.lab_6.ui.screens.images.ImagesScreen
import com.example.lab_6.ui.theme.Lab_6Theme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab_6Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val state = rememberPermissionState(Manifest.permission.READ_MEDIA_IMAGES)

                    when {
                        state.status.isGranted -> ImagesScreen(
                            modifier = Modifier.padding(
                                innerPadding
                            )
                        )

                        else -> {
                            LaunchedEffect(Unit) {
                                state.launchPermissionRequest()
                            }
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text("Что вы наделали...")
                            }
                        }
                    }
                }
            }
        }
    }
}