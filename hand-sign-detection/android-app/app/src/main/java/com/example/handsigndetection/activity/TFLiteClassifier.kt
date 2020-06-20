package com.example.handsigndetection.activity

import android.content.Context
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.gpu.GpuDelegate

class TFLiteClassifier(private val context: Context) {

    private var interpreter: Interpreter? = null
    var isInitialized = false
        private set

    private var gpuDelegate: GpuDelegate? = null

    var labels = ArrayList<String>()

    private val inputImageWidth: Int  = 0
    private val inputImageHeight: Int = 0
    private val modelInputSize: Int = 0

    private fun initializeInterpreter() {

        val assetManager = context.assets
        val model = loadModelFIle(assetManager, "")
    }
}