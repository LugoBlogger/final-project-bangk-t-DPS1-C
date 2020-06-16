package com.example.handsigndetection.adapters

import android.content.Context
import android.content.Intent
import com.example.handsigndetection.model.Image
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.handsigndetection.R
import com.example.handsigndetection.activity.PredictionActivity
import com.example.handsigndetection.model.Prediction

class ImageAdapters(var context: Context, private val images: ArrayList<Image>) : BaseAdapter() {

    companion object {
        val INTENT_PARCELABLE = "PREDICTION_INTENT"
    }

    override fun getItem(position: Int): Any {
        return images.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return images.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view: View = View.inflate(context,
            R.layout.card_image, null)
        var imageView: ImageView = view.findViewById(R.id.imageView)
        var titleTextView: TextView = view.findViewById(R.id.titleTextView)
        var subtitleTextView: TextView = view.findViewById(R.id.subtitleTextView)

        val image: Image = images[position]
        imageView.setImageResource(image.file)

        val prediction: Prediction = image.predictions[0]
        titleTextView.text = prediction.alphabet
        subtitleTextView.text = prediction.accuracy


        view.setOnClickListener {v: View ->
//            Toast.makeText(context, "Test", Toast.LENGTH_SHORT).show()
            val intent: Intent = Intent(v.context, PredictionActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra(INTENT_PARCELABLE, image)
            v.context.startActivity(intent)
        }

        return view
    }

}