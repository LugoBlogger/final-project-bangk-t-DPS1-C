package com.example.handsigndetection.service

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import java.io.ByteArrayOutputStream

class Utility() {
    fun bitmapToByteArray(bitmap: Bitmap) : ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        val result: ByteArray = stream.toByteArray()
        return result
    }

    fun byteArrayToBitmap(byteArray: ByteArray?): Bitmap {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray!!.lastIndex - 1)
    }
}