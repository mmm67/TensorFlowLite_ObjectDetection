package com.example.objectdetection.data

import android.content.Context
import android.graphics.Bitmap
import com.example.objectdetection.domain.AppObjectDetector
import com.example.objectdetection.domain.DetectedObject
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.image.ops.Rot90Op
import org.tensorflow.lite.task.core.BaseOptions
import org.tensorflow.lite.task.vision.detector.ObjectDetector

class TfLiteObjectDetector(
    private val context: Context,
    private val confidenceThreshold: Float = .5f,
    private val threadNumbers: Int = 2
) : AppObjectDetector {

    companion object {
        const val INPUT_IMAGE_SIZE = 320
        const val MODEL_NAME = "EfficientDet-Lite.tflite"
    }

    private var objectDetector: ObjectDetector? = null

    private fun setupDetector() {
        val baseOptions = BaseOptions.builder()
            .setNumThreads(threadNumbers)
            .build()

        val options = ObjectDetector.ObjectDetectorOptions.builder()
            .setBaseOptions(baseOptions)
            .setScoreThreshold(confidenceThreshold)
            .build()

        try {
            objectDetector = ObjectDetector.createFromFileAndOptions(
                context, MODEL_NAME, options
            )
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }
    }


    override fun detect(bitmap: Bitmap, rotation: Int): List<DetectedObject> {
        if (objectDetector == null) {
            setupDetector()
        }

        val imageProcessor =
            org.tensorflow.lite.support.image.ImageProcessor.Builder().add(Rot90Op(-rotation / 90))
                .add(
                    ResizeOp(INPUT_IMAGE_SIZE, INPUT_IMAGE_SIZE, ResizeOp.ResizeMethod.BILINEAR)
                ).build()

        val tensorImage =
            imageProcessor.process(TensorImage.fromBitmap(bitmap))

        val result = objectDetector?.detect(tensorImage)

        return result?.flatMap { classifications ->
            classifications.categories.map { category ->
                DetectedObject(
                    name = category.label,
                    score = category.score,
                    classifications.boundingBox
                )
            }
        }?.distinctBy { it.name } ?: emptyList()
    }
}


