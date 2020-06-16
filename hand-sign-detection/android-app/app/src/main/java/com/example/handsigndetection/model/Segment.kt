package com.example.handsigndetection.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Segment (
    val date: String,
    val result: String,
    val average: String,
    val images: ArrayList<Image>
) : Parcelable