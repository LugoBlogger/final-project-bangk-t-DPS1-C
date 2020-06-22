package com.example.handsigndetection.handler

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.handsigndetection.model.Segment

class SegmentHandler (context: Context) : SQLiteOpenHelper(context, SegmentHandler.DB_NAME, null, SegmentHandler.DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_TABLE = "CREATE TABLE $TABLE_NAME ($ID INTEGER PRIMARY KEY NOT NULL, $RESULT TEXT NOT NULL, $AVERAGE TEXT NOT NULL, $DATE TEXT NOT NULL);"
        db.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
        db.execSQL(DROP_TABLE)
        onCreate(db)
    }

    fun create(segment: Segment): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(RESULT, segment.result)
        values.put(AVERAGE, segment.average)
        values.put(DATE, segment.date)
        val _success = db.insert(TABLE_NAME, null, values)
        db.close()
        Log.v("InsertedIdSegment", "$_success")
        return Integer.parseInt("$_success")
    }

    fun read(_id: Int): Segment {
        val segment: Segment = Segment()
        val db = writableDatabase
        val selectQuery = "SELECT  * FROM $TABLE_NAME WHERE $ID = $_id"
        val cursor = db.rawQuery(selectQuery, null)

        cursor?.moveToFirst()
        segment.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
        segment.result = cursor.getString(cursor.getColumnIndex(RESULT))
        segment.average = cursor.getString(cursor.getColumnIndex(AVERAGE))
        segment.date = cursor.getString(cursor.getColumnIndex(DATE))
        cursor.close()
        return segment
    }

    fun readAll(): ArrayList<Segment> {
        val segments = ArrayList<Segment>()
        val db = writableDatabase
        val selectQuery = "SELECT  * FROM $TABLE_NAME"
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val segment: Segment = Segment()
                    segment.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
                    segment.result = cursor.getString(cursor.getColumnIndex(RESULT))
                    segment.average = cursor.getString(cursor.getColumnIndex(AVERAGE))
                    segment.date = cursor.getString(cursor.getColumnIndex(DATE))
                    segments.add(segment)
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        return segments
    }

    fun update(segment: Segment): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(RESULT, segment.result)
        values.put(AVERAGE, segment.average)
        values.put(DATE, segment.date)
        val _success = db.update(TABLE_NAME, values, ID + "=?", arrayOf(segment.id.toString())).toLong()
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

    companion object {
        private val DB_VERSION = 1
        private val DB_NAME = "HAGN"
        private val TABLE_NAME = "Segment"
        private val ID = "Id"
        private val RESULT = "Result"
        private val AVERAGE = "Average"
        private val DATE = "Date"
    }
}