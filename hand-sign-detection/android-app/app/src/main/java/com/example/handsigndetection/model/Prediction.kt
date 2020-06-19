package com.example.handsigndetection.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Prediction(
    var id: Int = 0,
    var alphabet: String = "",
    var accuracy: String = "",
    var imageId: Int = -1
) : Parcelable