package com.example.handsigndetection.handler

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import com.example.handsigndetection.model.Image
import java.util.function.Predicate

class ImageHandler (context: Context) : SQLiteOpenHelper(context, ImageHandler.DB_NAME, null, ImageHandler.DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_TABLE = "CREATE TABLE $TABLE_NAME ($ID INTEGER PRIMARY KEY NOT NULL, $FILE BLOB NOT NULL, $SEGMENT_ID INTEGER NOT NULL);"
        db.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
        db.execSQL(DROP_TABLE)
        onCreate(db)
    }

    fun create(image: Image): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(FILE, image.file)
        values.put(SEGMENT_ID, image.segmentId)
        val _success = db.insert(TABLE_NAME, null, values)
        db.close()
        Log.v("InsertedIdImage", "$_success")
        return Integer.parseInt("$_success")
    }

    fun read(_id: Int): Image {
        val image: Image = Image()
        val db = writableDatabase
        val selectQuery = "SELECT  * FROM $TABLE_NAME WHERE $ID = $_id"
        val cursor = db.rawQuery(selectQuery, null)

        cursor?.moveToFirst()
        image.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
        image.file = cursor.getBlob(cursor.getColumnIndex(FILE))
        image.segmentId = Integer.parseInt(cursor.getString(cursor.getColumnIndex(SEGMENT_ID)))
        cursor.close()
        return image
    }

    fun readAll(): ArrayList<Image> {
        val segments = ArrayList<Image>()
        val db = writableDatabase
        val selectQuery = "SELECT  * FROM $TABLE_NAME"
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val image: Image = Image()
                    image.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
                    image.file = cursor.getBlob(cursor.getColumnIndex(FILE))
                    image.segmentId = Integer.parseInt(cursor.getString(cursor.getColumnIndex(SEGMENT_ID)))
                    segments.add(image)
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        return segments
    }

    fun update(image: Image): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(FILE, image.file)
        values.put(SEGMENT_ID, image.segmentId)
        val _success = db.update(TABLE_NAME, values, ID + "=?", arrayOf(image.id.toString())).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1
    }

    fun delete(_id: Int): Boolean {
        val db = this.writableDatabase
        val _success = db.delete(TABLE_NAME, ID + "=?", arrayOf(_id.toString())).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1
    }

    fun deleteAll(): Boolean {
        val db = this.writableDatabase
        val _success = db.delete(TABLE_NAME, null, null).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1
    }

    fun collectBySegment(id: Int): ArrayList<Image> {
        var images = readAll()
        var result: ArrayList<Image> = ArrayList<Image>()
        for (image in images) {
            if (image.segmentId == id) {
                result.add(image)
            }
        }
        return result
    }

    companion object {
        private val DB_VERSION = 2
        private val DB_NAME = "HAGNDB"
        private val TABLE_NAME = "Image"
        private val ID = "Id"
        private val FILE = "File"
        private val SEGMENT_ID = "SegmentId"
    }
}