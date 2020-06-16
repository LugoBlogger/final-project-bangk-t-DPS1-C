package com.example.handsigndetection.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridView
import android.widget.ImageView
import com.example.handsigndetection.adapters.PredictionAdapters
import com.example.handsigndetection.R
import com.example.handsigndetection.model.Image
import com.example.handsigndetection.model.Prediction

class PredictionActivity : AppCompatActivity() {
    private var predictions: ArrayList<Prediction>? = null
    private var gridView: GridView? = null
    private var predictionAdapters: PredictionAdapters? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prediction)

        val image: Image = intent.getParcelableExtra<Image>("PREDICTION_INTENT")

        var imageView: ImageView = findViewById(R.id.imageView)
        imageView.setImageResource(image.file)

        predictions = image.predictions

        gridView = findViewById(R.id.gridView)
        predictionAdapters =
            PredictionAdapters(
                applicationContext,
                predictions!!
            )
        gridView?.adapter = predictionAdapters

        val actionbar = supportActionBar
        actionbar!!.title = "Detail Image"
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}