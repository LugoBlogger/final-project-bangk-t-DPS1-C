package com.example.handsigndetection.activity

import android.os.Bundle
import android.widget.GridView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.example.handsigndetection.adapters.NewSegmentAdapters
import com.example.handsigndetection.R
import com.example.handsigndetection.model.Image
import com.example.handsigndetection.model.Prediction

class NewSegmentActivity : AppCompatActivity() {
    private var images: ArrayList<Image>? = null
    private var gridView: GridView? = null
    private var newSegmentAdapters: NewSegmentAdapters? = null

//    companion object {
//        //image pick code
//        private val IMAGE_PICK_CODE = 1000;
//        //Permission code
//        private val PERMISSION_CODE = 1001;
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_segment)

        val actionbar = supportActionBar
        actionbar!!.title = "New Segment"
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)

        images = getDataList()

        gridView = findViewById(R.id.gridView)
        newSegmentAdapters=
            NewSegmentAdapters(
                applicationContext,
                images!!
            )
        gridView?.adapter = newSegmentAdapters

        findViewById<FloatingActionButton>(R.id.addFab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
//                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
//                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
//                    requestPermissions(permissions, PERMISSION_CODE);
//                } else {
//                    pickImageFromGallery();
//                }
//            } else{
//                pickImageFromGallery();
//            }
        }

        findViewById<FloatingActionButton>(R.id.doneFab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

//    private fun pickImageFromGallery() {
//        val intent = Intent(Intent.ACTION_PICK)
//        intent.type = "image/*"
//        startActivityForResult(intent, IMAGE_PICK_CODE)
//    }
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        when(requestCode){
//            PERMISSION_CODE -> {
//                if (grantResults.size >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                    pickImageFromGallery()
//                } else {
//                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
//            images.add(data?.data, ArrayList<Prediction>)
//        }
//    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun getDataList(): ArrayList<Image> {
        var predictions: ArrayList<Prediction> = ArrayList()
        predictions.add(Prediction("N", "0.96"))
        predictions.add(Prediction("M", "0.86"))
        predictions.add(Prediction("0", "0.76"))

        var images: ArrayList<Image> = ArrayList()
        images.add(Image(R.drawable.n, predictions))
        images.add(Image(R.drawable.e, predictions))
        images.add(Image(R.drawable.w, predictions))
        return images
    }
}