package com.example.handsigndetection.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.GridView
import android.widget.TextView
import android.widget.Toast
import com.example.handsigndetection.R
import com.example.handsigndetection.adapters.ImageAdapters
import com.example.handsigndetection.model.Image
import com.example.handsigndetection.model.Segment

class ImageActivity : AppCompatActivity() {
    private var images: ArrayList<Image>? = null
    private var gridView: GridView? = null
    private var imageAdapters: ImageAdapters? = null

//    companion object {
//        val INTENT_PARCELABLE = "PREDICTION_INTENT"
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        val segment: Segment = intent.getParcelableExtra<Segment>("IMAGE_INTENT")
        images = segment.images

        var dateTextView: TextView = findViewById(R.id.dateTextView)
        dateTextView.text = segment.date

        var resultTextView: TextView = findViewById(R.id.resultTextView)
        resultTextView.text = segment.result

        var averageTextView: TextView = findViewById(R.id.accuracyTextView)
        averageTextView.text = segment.average

        gridView = findViewById(R.id.gridView)
        imageAdapters = ImageAdapters(
            applicationContext,
            images!!
        )
        gridView?.adapter = imageAdapters

        val actionbar = supportActionBar
        actionbar!!.title = "Detail Segment"
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)


//        gridView?.onItemClickListener = this
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

//    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//        val image: Image = images!!.get(position)
//        Toast.makeText(applicationContext, "Test", Toast.LENGTH_SHORT).show()
////        val intent: Intent = Intent(applicationContext, PredictionActivity::class.java)
////        intent.putExtra(INTENT_PARCELABLE, image)
////        applicationContext.startActivity(intent)
//    }

}