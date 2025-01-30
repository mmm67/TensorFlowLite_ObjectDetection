package com.example.objectdetection.presentation

import android.graphics.Bitmap
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.example.objectdetection.domain.DetectedObject
import com.example.objectdetection.domain.AppObjectDetector

class ImageAnalyzer(
    private val detector: AppObjectDetector,
    val onResults: (List<DetectedObject>) -> Unit
) : ImageAnalysis.Analyzer {
    lateinit var bitmapBuffer: Bitmap

    @OptIn(ExperimentalGetImage::class)
    override fun analyze(image: ImageProxy) {
        if (!::bitmapBuffer.isInitialized) {
            bitmapBuffer = Bitmap.createBitmap(
                image.width,
                image.height,
                Bitmap.Config.ARGB_8888
            )
        }

        val rotationDegrees = image.imageInfo.rotationDegrees
        image.use { bitmapBuffer.copyPixelsFromBuffer(image.planes[0].buffer) }
        val results = detector.detect(bitmapBuffer, rotationDegrees)
        onResults(results)
    }
}
