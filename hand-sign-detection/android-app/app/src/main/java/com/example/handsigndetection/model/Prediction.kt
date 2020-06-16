package com.example.handsigndetection.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Prediction(
    val alphabet: String,
    val accuracy: String
) : Parcelable