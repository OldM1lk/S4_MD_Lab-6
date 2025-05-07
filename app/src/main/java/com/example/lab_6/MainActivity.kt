package com.example.lab_6

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.lab_6.ui.screens.RequestPermission
import com.example.lab_6.ui.screens.details.DetailsScreen
import com.example.lab_6.ui.screens.gallery.GalleryScreen
import com.example.lab_6.ui.screens.gallery.GalleryViewModel
import com.example.lab_6.ui.theme.Lab_6Theme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab_6Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    val galleryViewModel: GalleryViewModel = koinViewModel()
                    val imageState by galleryViewModel.state.collectAsState()
                    val permissionState =
                        rememberPermissionState(Manifest.permission.READ_MEDIA_IMAGES)

                    NavHost(
                        navController = navController,
                        startDestination = RequestPermission
                    ) {
                        composable<RequestPermission> {
                            RequestPermission(
                                navController = navController,
                                permissionState = permissionState
                            )
                        }
                        composable<GalleryScreen> {
                            galleryViewModel.loadImages()
                            GalleryScreen(
                                navController = navController,
                                state = imageState,
                                onEvent = galleryViewModel::onEvent,
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                        composable<DetailScreen> {
                            val args = it.toRoute<DetailScreen>()
                            DetailsScreen(
                                description = args.description,
                                uri = args.uri,
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Serializable
object RequestPermission

@Serializable
object GalleryScreen

@Serializable
data class DetailScreen(
    val id: Long,
    val description: String?,
    val uri: String
)