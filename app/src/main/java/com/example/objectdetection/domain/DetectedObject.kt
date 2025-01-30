package com.example.objectdetection.domain

import android.graphics.RectF

data class DetectedObject(
    val name: String,
    val score: Float,
    val boundingBox: RectF
)