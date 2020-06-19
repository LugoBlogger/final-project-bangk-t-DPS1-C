package com.example.handsigndetection.activity

import android.Manifest
import android.app.Activity
import android.content.ClipData
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.GridView
import android.widget.ImageView
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AppCompatActivity
import com.example.handsigndetection.adapters.NewSegmentAdapters
import com.example.handsigndetection.R
import com.example.handsigndetection.service.Utility
import com.example.handsigndetection.handler.ImageHandler
import com.example.handsigndetection.handler.PredictionHandler
import com.example.handsigndetection.handler.SegmentHandler
import com.example.handsigndetection.model.Image
import com.example.handsigndetection.model.Prediction
import com.example.handsigndetection.model.Segment
import java.io.InputStream
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class NewSegmentActivity : AppCompatActivity() {
    private var bitmaps: ArrayList<Bitmap>? = null
    private var images: ArrayList<Image>? = null
    private var gridView: GridView? = null
    private var newSegmentAdapters: NewSegmentAdapters? = null
    private var utility: Utility? = null
    private var segmentHandler: SegmentHandler? = null
    private var segmentId: Int? = -1
    private var imageHandler: ImageHandler? = null
    private var predictionHandler: PredictionHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_segment)
        initObject()
        initActionBar()
        initUploadButton()
        initSaveButton()
    }



    // ALL ABOUT INITIALIZATION OF UI COMPONENT (START)
    private fun initObject() {
        utility = Utility()
        bitmaps = ArrayList<Bitmap>()
        images = ArrayList<Image>()
        segmentHandler = SegmentHandler(this)
        imageHandler = ImageHandler(this)
        predictionHandler = PredictionHandler(this)
        gridView = findViewById(R.id.gridView)
    }

    private fun reloadGridView() {
        newSegmentAdapters = NewSegmentAdapters(this, bitmaps!!)
        gridView?.adapter = newSegmentAdapters
    }

    private fun initActionBar() {
        val actionbar = supportActionBar
        actionbar!!.title = "New Segment"
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    // ALL ABOUT INITIALIZATION OF UI COMPONENT (END)



    // FLOATING ACTION BUTTON FUNCTION (START)
    private fun initUploadButton() {
        findViewById<FloatingActionButton>(R.id.addFab).setOnClickListener { view ->
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                requestPermissions(permissions, PERMISSION_CODE);
            } else{
                pickImageFromGallery();
            }
        }
    }

    private fun initSaveButton() {
        findViewById<FloatingActionButton>(R.id.doneFab).setOnClickListener { view ->
            if (bitmaps == null) {
                Toast.makeText(this, "You must upload photo first!", Toast.LENGTH_LONG).show()
            } else {
                saveSegment()
                saveImages()
                val intent: Intent = Intent(applicationContext, SegmentActivity::class.java)
                startActivity(intent)
            }
        }
    }
    // FLOATING ACTION BUTTON FUNCTION (END)



    // INSERT DATA INTO SQLITE DATABASE (START)
    private fun saveSegment() {
        val segment: Segment = Segment()
        segment.result = "Bangkit Academy"
        segment.average = "0.0"
        segment.date = SimpleDateFormat("dd/M/yyyy hh:mm:ss").format(Date()).toString()
        segmentId = segmentHandler?.create(segment)
    }

    private fun saveImages() {
        for (bitmap in bitmaps!!) {
            val image: Image = Image()
            image.file = utility?.bitmapToByteArray(bitmap)
            image.segmentId = segmentId!!
            val imageId = imageHandler?.create(image)
            if (imageId != -1) {
                images?.add(image)
                // start looping for predictions
                // for (...) {
                var success: Boolean = false
                val prediction: Prediction = Prediction()
                prediction.imageId = imageId!!
                prediction.accuracy = "0.0"
                prediction.alphabet = "B"
                success = predictionHandler?.create(prediction) as Boolean
                // }
                // end looping for predictions
            }
        }
    }
    // INSERT DATA INTO SQLITE DATABASE (END)

    // TAKE IMAGES FROM GALLERY PROCESS (START)
    companion object {
        private val IMAGE_PICK_CODE = 1;
        private val PERMISSION_CODE = 1001;
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    pickImageFromGallery()
                } else{
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE && data != null){
            if (data?.clipData != null) {
                pickMultiple(data)
            } else {
                pickSingle(data)
            }
            reloadGridView()
        }
    }

    private fun pickMultiple(data: Intent?) {
        var count: Int = data?.clipData!!.itemCount
        for (i in 0..count - 1) {
            val uri: Uri? =  data.clipData!!.getItemAt(i).uri
            val inputStream: InputStream? = contentResolver.openInputStream(uri!!)
            val bitmap: Bitmap = BitmapFactory.decodeStream(inputStream)
            bitmaps?.add(bitmap)
        }
    }

    private fun pickSingle(data: Intent?) {
        val uri: Uri? = data?.data
        val inputStream: InputStream? = contentResolver.openInputStream(uri!!)
        val bitmap: Bitmap = BitmapFactory.decodeStream(inputStream)
        bitmaps?.add(bitmap)
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }
    // TAKE IMAGES FROM GALLERY PROCESS (END)

}