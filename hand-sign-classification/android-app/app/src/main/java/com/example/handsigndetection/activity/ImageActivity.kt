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
import com.example.handsigndetection.handler.ImageHandler
import com.example.handsigndetection.model.Image
import com.example.handsigndetection.model.Segment

class ImageActivity : AppCompatActivity() {
    private var images: ArrayList<Image>? = ArrayList<Image>()
    private var gridView: GridView? = null
    private var imageAdapters: ImageAdapters? = null
    private var imageHandler: ImageHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)
        initInformation()
        initGridView()
        initActionBar()
    }



    // ALL ABOUT INITIALIZATION OF UI COMPONENT (START)
    private fun initInformation() {
        val segment: Segment = intent.getParcelableExtra<Segment>("IMAGE_INTENT")
        imageHandler = ImageHandler(this)
        images = imageHandler?.collectBySegment(segment.id)

        var dateTextView: TextView = findViewById(R.id.dateTextView)
        dateTextView.text = segment.date

        var resultTextView: TextView = findViewById(R.id.resultTextView)
        resultTextView.text = segment.result

        var averageTextView: TextView = findViewById(R.id.accuracyTextView)
        averageTextView.text = segment.average

    }

    private fun initGridView() {
        gridView = findViewById(R.id.gridView)
        imageAdapters = ImageAdapters(this, images!!)
        gridView?.adapter = imageAdapters
    }

    private fun initActionBar() {
        val actionbar = supportActionBar
        actionbar!!.title = "Detail Segment"
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    // ALL ABOUT INITIALIZATION OF UI COMPONENT (END)

}