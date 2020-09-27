package com.example.a7minuteworkout

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.util.*
import kotlin.collections.ArrayList

class SqliteOpenHelper(context: Context,factory: SQLiteDatabase.CursorFactory?) : SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION){


companion object{

    private val DATABASE_VERSION = 1
    private val DATABASE_NAME= "sevenMinutesWorkout.db"
    private val TABLE_NAME = "history"
    private val COLUMN_ID = "_id"                  // _ primary key
    private val COLUMN_DATE = "date"


}

    override fun onCreate(db: SQLiteDatabase?) {

       // val CREATE_TABLE = ("CREATE TABLE" + TABLE_NAME + "(" + COLUMN_ID + "INTEGER PRIMARY KEY," + COLUMN_DATE + " TEXT)")
        db?.execSQL("CREATE TABLE history(id INTEGER PRIMARY KEY, date TEXT)")

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        db?.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME)
            onCreate(db)


    }

     fun addDate(date:String)
    {
        val values =ContentValues()
        values.put(COLUMN_DATE,date)
        val db= this.writableDatabase
        db.insert(TABLE_NAME,null,values)
        db.close()
    }

    fun gelAll(): ArrayList<String>
    {
        val list=ArrayList<String>()
        val db= this.readableDatabase
        val cursor= db.rawQuery("SELECT * FROM $TABLE_NAME",null)

        while (cursor.moveToNext())                      //enquanto existir um proximo registro
        {
            val dateValue = cursor.getString(cursor.getColumnIndex(COLUMN_DATE))        // pega o txt da coluna date
            list.add(dateValue)
        }
        cursor.close()
        return list
    }

    


}