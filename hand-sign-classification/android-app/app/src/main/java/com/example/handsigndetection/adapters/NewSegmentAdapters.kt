package com.example.handsigndetection.adapters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.example.handsigndetection.R
import com.example.handsigndetection.service.Utility
import com.example.handsigndetection.model.Image

class NewSegmentAdapters(var context: Context, private val images: ArrayList<Bitmap>) : BaseAdapter() {
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
        val view: View = View.inflate(context, R.layout.card_new_image, null)
        val imageView: ImageView = view.findViewById(R.id.imageView)
        imageView.setImageBitmap(images.get(position))
        return view
    }
}