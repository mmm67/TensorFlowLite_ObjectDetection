package com.example.objectdetection.domain

import android.graphics.Bitmap

interface AppObjectDetector {
    fun detect(bitmap: Bitmap, rotation: Int) : List<DetectedObject>
}