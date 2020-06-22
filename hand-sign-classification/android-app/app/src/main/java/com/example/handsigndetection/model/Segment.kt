package com.example.handsigndetection.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Segment (
    var id: Int = 0,
    var date: String = "",
    var result: String = "",
    var average: String = ""
) : Parcelable