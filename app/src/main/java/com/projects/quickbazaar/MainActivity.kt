package com.projects.quickbazaar

import com.google.firebase.FirebaseApp
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.unit.*

import androidx.navigation.compose.rememberNavController
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.projects.quickbazaar.ui.theme.QuickBazaarTheme
import kotlinx.coroutines.Dispatchers

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContent {
            val context= LocalContext.current
            val nav_control = rememberNavController()
            val networkMonitor= remember {
                mutableStateOf(NetworkMonitor(this))
            }
            val screen_controller = ScreensControllerHost(nav_control, networkMonitor.value)
            screen_controller.AppNavHost(context)
            /*var lol = "https://randomuser.me/api/portraits/men/32.jpg"
            AsyncImage(
                model =
                ImageRequest
                    .Builder(LocalContext.current)
                    .data(lol)
                    .build(),
                contentDescription = "Profile Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
            )
            */


        }
    }
}

