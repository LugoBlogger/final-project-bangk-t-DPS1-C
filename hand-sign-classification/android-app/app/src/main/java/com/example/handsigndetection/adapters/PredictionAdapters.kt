package com.example.handsigndetection.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.handsigndetection.R
import com.example.handsigndetection.model.Prediction

class PredictionAdapters(var context: Context, private val predictions: ArrayList<Prediction>) : BaseAdapter() {
    override fun getItem(position: Int): Any {
        return predictions.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return predictions.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view: View = View.inflate(context, R.layout.card_prediction, null)
        var rankTextView: TextView = view.findViewById(R.id.rankTextView)
        var alphabetTextView: TextView = view.findViewById(R.id.alphabetTextView)
        var accuracyTextView: TextView = view.findViewById(R.id.accuracyTextView)

        val prediction: Prediction = predictions[position]
        rankTextView.text = position.toString()
        alphabetTextView.text = prediction.alphabet
        accuracyTextView.text = prediction.accuracy

        return view
    }

}