package com.example.objectdetection.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.objectdetection.data.TfLiteObjectDetector.Companion.INPUT_IMAGE_SIZE
import com.example.objectdetection.domain.DetectedObject

@Composable
fun ObjectRecognitionScreen(
    modifier: Modifier = Modifier
) {
    var detectedObjects by remember {
        mutableStateOf(emptyList<DetectedObject>())
    }

    Box(modifier = modifier.fillMaxSize()) {
        CameraPreview(onResult = {detectedObjects = it})
        if (detectedObjects.isNotEmpty()) {
            DetectedObjectBoundingBox(
                detectedObjects,
                INPUT_IMAGE_SIZE.toFloat(),
                INPUT_IMAGE_SIZE.toFloat()
            )
        }
    }
}
