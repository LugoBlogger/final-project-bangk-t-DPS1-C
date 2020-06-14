package com.example.handsigndetection.adapters
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.handsigndetection.activity.ImageActivity
import com.example.handsigndetection.R
import com.example.handsigndetection.model.Segment

class SegmentAdapters(private val segments: ArrayList<Segment>): RecyclerView.Adapter<SegmentAdapters.ViewHolder>() {

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
        val segment: Segment = segments[position]
        holder.titleTextView.text = segment.result
        holder.subtitleTextView.text = segment.average
        holder.imageView.setImageResource(segment.images[0].file)
        holder.imageView2.setImageResource(segment.images[1].file)
        holder.imageView3.setImageResource(segment.images[2].file)

        holder.itemView.setOnClickListener { view: View ->
            val intent: Intent = Intent(view.context, ImageActivity::class.java)
            intent.putExtra(INTENT_PARCELABLE, segment)
            view.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return segments.size
    }

}