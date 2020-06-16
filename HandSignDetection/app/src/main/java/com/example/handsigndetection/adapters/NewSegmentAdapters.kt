package com.example.handsigndetection.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.example.handsigndetection.R
import com.example.handsigndetection.model.Image

class NewSegmentAdapters(var context: Context, private val images: ArrayList<Image>) : BaseAdapter() {
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
            R.layout.card_new_image, null)
        var imageView: ImageView = view.findViewById(R.id.imageView)
        imageView.setImageResource(images[position].file)
        return view
    }
}