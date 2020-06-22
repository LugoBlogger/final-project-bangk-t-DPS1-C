package com.example.handsigndetection.adapters
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.handsigndetection.activity.ImageActivity
import com.example.handsigndetection.R
import com.example.handsigndetection.service.Utility
import com.example.handsigndetection.handler.ImageHandler
import com.example.handsigndetection.model.Image
import com.example.handsigndetection.model.Segment

class SegmentAdapters(var context: Context, private val segments: ArrayList<Segment>): RecyclerView.Adapter<SegmentAdapters.ViewHolder>() {

    companion object {
        val INTENT_PARCELABLE = "IMAGE_INTENT"
    }

    inner class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView
        var imageView2: ImageView
        var imageView3: ImageView
        var titleTextView: TextView
        var subtitleTextView: TextView

        init {
            imageView = itemView.findViewById(R.id.imageView)
            imageView2 = itemView.findViewById(R.id.imageView2)
            imageView3 = itemView.findViewById(R.id.imageView3)
            titleTextView = itemView.findViewById(R.id.titleTextView)
            subtitleTextView = itemView.findViewById(R.id.subtitleTextView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.card_segment, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val segment: Segment = segments.get(position)
        holder.titleTextView.text = segment.result
        holder.subtitleTextView.text = segment.date

        val imageHandler: ImageHandler = ImageHandler(context)
        var images: ArrayList<Image> = imageHandler.collectBySegment(segment.id)
        val utility: Utility = Utility()
        holder.imageView.setImageBitmap(utility.byteArrayToBitmap(images.get(0).file))
        holder.imageView2.setImageBitmap(utility.byteArrayToBitmap(images.get(1).file))
        holder.imageView3.setImageBitmap(utility.byteArrayToBitmap(images.get(2).file))

        holder.itemView.setOnClickListener { view: View ->
            val intent: Intent = Intent(context, ImageActivity::class.java)
            intent.putExtra(INTENT_PARCELABLE, segment)
            view.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return segments.size
    }

}