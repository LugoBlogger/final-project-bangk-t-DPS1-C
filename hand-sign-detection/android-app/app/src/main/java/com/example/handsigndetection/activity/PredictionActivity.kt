package com.example.handsigndetection.activity

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.GridView
import android.widget.ImageView
import com.example.handsigndetection.adapters.PredictionAdapters
import com.example.handsigndetection.R
import com.example.handsigndetection.service.Utility
import com.example.handsigndetection.handler.PredictionHandler
import com.example.handsigndetection.model.Image
import com.example.handsigndetection.model.Prediction

class PredictionActivity : AppCompatActivity() {
    private var predictions: ArrayList<Prediction>? = null
    private var gridView: GridView? = null
    private var predictionAdapters: PredictionAdapters? = null
    private var predictionHandler: PredictionHandler? = null

    private val TAG = "PredictionActivity"
    private var tfLiteClassifier: TFLiteClassifier = TFLiteClassifier(this@PredictionActivity)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prediction)
        initInformation()
        initGridView()
        initActionBar()

        tfLiteClassifier
            .initialize()
            .addOnSuccessListener { }
            .addOnFailureListener { e -> Log.e(TAG, "Error in setting up the classifier.", e) }
    }


    // ALL ABOUT INITIALIZATION OF UI COMPONENT (START)
    private fun initInformation() {
        val image: Image = intent.getParcelableExtra<Image>("PREDICTION_INTENT")

        var imageView: ImageView = findViewById(R.id.imageView)
        imageView.setImageBitmap(BitmapFactory.decodeByteArray(image.file, 0, image.file!!.lastIndex - 1))

        predictionHandler = PredictionHandler(this)
        predictions = predictionHandler?.collectByImage(image.id)
    }

    private fun initGridView() {
        gridView = findViewById(R.id.gridView)
        predictionAdapters = PredictionAdapters(applicationContext, predictions!!)
        gridView?.adapter = predictionAdapters
    }

    private fun initActionBar() {
        val actionbar = supportActionBar
        actionbar!!.title = "Detail Image"
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    // ALL ABOUT INITIALIZATION OF UI COMPONENT (END)
}