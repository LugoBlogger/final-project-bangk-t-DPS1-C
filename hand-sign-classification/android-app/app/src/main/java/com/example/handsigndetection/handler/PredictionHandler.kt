package com.example.handsigndetection.handler

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.handsigndetection.model.Prediction

class PredictionHandler (context: Context) : SQLiteOpenHelper(context, PredictionHandler.DB_NAME, null, PredictionHandler.DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_TABLE = "CREATE TABLE $TABLE_NAME ($ID INTEGER PRIMARY KEY NOT NULL, $ALPHABET TEXT NOT NULL, $ACCURACY TEXT NOT NULL, $IMAGE_ID INTEGER NOT NULL);"
        db.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
        db.execSQL(DROP_TABLE)
        onCreate(db)
    }

    fun create(prediction: Prediction): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(ALPHABET, prediction.alphabet)
        values.put(ACCURACY, prediction.accuracy)
        values.put(IMAGE_ID, prediction.imageId)
        val _success = db.insert(TABLE_NAME, null, values)
        db.close()
        Log.v("InsertedIdPrediction", "$_success")
        return (Integer.parseInt("$_success") != -1)
    }

    fun read(_id: Int): Prediction {
        val prediction: Prediction = Prediction()
        val db = writableDatabase
        val selectQuery = "SELECT  * FROM $TABLE_NAME WHERE $ID = $_id"
        val cursor = db.rawQuery(selectQuery, null)

        cursor?.moveToFirst()
        prediction.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
        prediction.alphabet = cursor.getString(cursor.getColumnIndex(ALPHABET))
        prediction.accuracy = cursor.getString(cursor.getColumnIndex(ACCURACY))
        prediction.imageId = Integer.parseInt(cursor.getString(cursor.getColumnIndex(IMAGE_ID)))
        cursor.close()
        return prediction
    }

    fun readAll(): ArrayList<Prediction> {
        val segments = ArrayList<Prediction>()
        val db = writableDatabase
        val selectQuery = "SELECT  * FROM $TABLE_NAME"
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val prediction: Prediction = Prediction()
                    prediction.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
                    prediction.alphabet = cursor.getString(cursor.getColumnIndex(ALPHABET))
                    prediction.accuracy = cursor.getString(cursor.getColumnIndex(ACCURACY))
                    prediction.imageId = Integer.parseInt(cursor.getString(cursor.getColumnIndex(IMAGE_ID)))
                    segments.add(prediction)
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        return segments
    }

    fun update(prediction: Prediction): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(ALPHABET, prediction.alphabet)
        values.put(ACCURACY, prediction.accuracy)
        values.put(IMAGE_ID, prediction.imageId)
        val _success = db.update(TABLE_NAME, values, ID + "=?", arrayOf(prediction.id.toString())).toLong()
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

    fun collectByImage(id: Int) : ArrayList<Prediction> {
        var predictions = readAll()
        var result: ArrayList<Prediction> = ArrayList<Prediction>()
        for (prediction in predictions) {
            if (prediction.imageId.equals(id)) {
                result.add(prediction)
            }
        }
        return result
    }

    companion object {
        private val DB_VERSION = 1
        private val DB_NAME = "HAGNApps"
        private val TABLE_NAME = "Prediction"
        private val ID = "Id"
        private val ALPHABET = "Alphabet"
        private val ACCURACY = "Accuracy"
        private val IMAGE_ID = "ImageId"
    }
}