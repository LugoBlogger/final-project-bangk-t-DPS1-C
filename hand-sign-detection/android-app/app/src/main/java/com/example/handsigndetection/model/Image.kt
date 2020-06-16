package com.example.handsigndetection.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Image (
    val file: Int,
    val predictions: ArrayList<Prediction>
) : Parcelable