package ru.gorinih.moneymana.data.camera

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.mlkit.vision.barcode.Barcode
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.Executor

class ManaCameraX(
    private val context: Context,
    private val executor: Executor,
    private val previewView: PreviewView,
    private val lifecycle: LifecycleOwner
) {

    private var cameraLens = CameraSelector.LENS_FACING_BACK
    private lateinit var imageCapture: ImageCapture
    private lateinit var inputImage: InputImage

    private lateinit var imageAnalysis: ImageAnalysis
    private lateinit var bitmapBuffer: Bitmap
    private var imageRotationDegrees: Int = 0

    private var _qrCode = MutableLiveData<String>()
    val qrCode: LiveData<String>
        get() = _qrCode

    fun bindCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        cameraProviderFuture.addListener(
            {
                val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
                bindPreview(cameraProvider)
            },
            executor
        )
    }

    @SuppressLint("UnsafeExperimentalUsageError", "UnsafeOptInUsageError")
    private fun bindPreview(cameraProvider: ProcessCameraProvider) {
        val preview = Preview.Builder()
            .setTargetAspectRatio(AspectRatio.RATIO_16_9)
            .setTargetRotation(previewView.display.rotation)
            .build()
            .also {
                it.setSurfaceProvider(previewView.surfaceProvider)
            }
        val cameraSelector = CameraSelector.Builder()
            .requireLensFacing(cameraLens)
            .build()
        imageCapture = ImageCapture.Builder()
            .setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY)
            .setTargetRotation(previewView.display.rotation)
            .build()

        imageAnalysis = ImageAnalysis.Builder()
            .setTargetAspectRatio(AspectRatio.RATIO_16_9)
            .setTargetRotation(previewView.display.rotation)
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()

        val converter = YuvToRgbConverter(previewView.context)

        imageAnalysis.setAnalyzer(executor, { image ->

            if (!::bitmapBuffer.isInitialized) { //once started
                imageRotationDegrees = image.imageInfo.rotationDegrees
                bitmapBuffer = Bitmap.createBitmap(
                    image.width, image.height, Bitmap.Config.ARGB_8888
                )
            }

            converter.yuvToRgb(image.image!!, bitmapBuffer)
            inputImage = InputImage.fromBitmap(bitmapBuffer, imageRotationDegrees)
            scanImage()
            image.close()
        })
        cameraProvider.bindToLifecycle(
            lifecycle,
            cameraSelector,
            imageCapture,
            preview, imageAnalysis
        )
    }

    private fun scanImage() {
        val options = BarcodeScannerOptions.Builder()
            .setBarcodeFormats(
                Barcode.FORMAT_QR_CODE
            )
            .build()
        val scanner = BarcodeScanning.getClient(options)
        if (::inputImage.isInitialized) {
            scanner.process(inputImage)
                .addOnSuccessListener { barcodes ->
                    for (barcode in barcodes) {
                        when (barcode.valueType) {
                            Barcode.TYPE_TEXT -> {
                                _qrCode.value = barcode.displayValue
                            }
                        }
                    }
                }
                .addOnFailureListener {
                    throw (it)
                }
        }
    }
}