package com.example.lab_6.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.lab_6.GalleryScreen
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted


@Composable
@OptIn(ExperimentalPermissionsApi::class)
fun RequestPermission(
    navController: NavController,
    permissionState: PermissionState
) {
    when {
        permissionState.status.isGranted -> {
            navController.navigate(GalleryScreen)
        }

        else -> {
            LaunchedEffect(Unit) {
                permissionState.launchPermissionRequest()
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