package com.example.objectdetection

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.objectdetection.presentation.CameraPermissionHandler
import com.example.objectdetection.presentation.ObjectRecognitionScreen
import com.example.objectdetection.ui.theme.ObjectDetectionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ObjectDetectionTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    var permissionGranted by remember { mutableStateOf(false) }

                    CameraPermissionHandler {
                        permissionGranted = true
                    }

                    if (permissionGranted) {
                        ObjectRecognitionScreen(Modifier.padding(innerPadding))
                    }
                }
            }
        }
    }
}
