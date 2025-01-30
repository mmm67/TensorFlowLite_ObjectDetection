package com.example.objectdetection.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.sp
import com.example.objectdetection.domain.DetectedObject


@Composable
fun DetectedObjectBoundingBox(
    detectedObjects: List<DetectedObject>,
    imageWidth: Float,
    imageHeight: Float
) {
    val textMeasurer = rememberTextMeasurer()

    Canvas(modifier = Modifier.fillMaxSize()) {
        val scaleX = size.width / imageWidth
        val scaleY = size.height / imageHeight

        detectedObjects.forEach { detectedObject ->
            val boundingBox = detectedObject.boundingBox

            val left = boundingBox.left * scaleX
            val top = boundingBox.top * scaleY
            val right = boundingBox.right * scaleX
            val bottom = boundingBox.bottom * scaleY

            drawRect(
                color = Color.Red,
                topLeft = Offset(left, top),
                size = Size(right - left, bottom - top),
                style = Stroke(width = 4f)
            )

            val textLayoutResult: TextLayoutResult =
                textMeasurer.measure(
                    text = "${detectedObject.name} (${"%.2f".format(detectedObject.score)})",
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
                )

            // Move the text above the bounding box by adjusting the Y position
            val textYPos = top - textLayoutResult.size.height - 10f

            drawText(
                textLayoutResult = textLayoutResult,
                topLeft = Offset(x = left, y = textYPos)
            )
        }
    }
}
