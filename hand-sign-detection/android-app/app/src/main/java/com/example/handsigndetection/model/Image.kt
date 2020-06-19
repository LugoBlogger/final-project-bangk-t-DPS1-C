package com.example.handsigndetection.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Image (
    var id: Int = 0,
    var file: ByteArray? = null,
    var segmentId: Int = -1
) : Parcelable