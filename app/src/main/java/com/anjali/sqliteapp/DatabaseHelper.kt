package com.anjali.sqliteapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


/**
 * Created by ProgrammingKnowledge on 4/3/2015.
 */
class DatabaseHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {//constructor for DatabaseHelper class which when called database is created locally

    //methods of SQLiteOpenHelper class
    override fun onCreate(db: SQLiteDatabase) {
        //table created
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,SURNAME TEXT,MARKS INTEGER)")
    }

    //methods of SQLiteOpenHelper class
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

        //to drop table
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        //creating table again
        onCreate(db)
    }

    fun insertData(name: String?, surname: String?, marks: String?): Boolean {
        val db = this.writableDatabase//same as db of onCreate() mthod above used to query database,instance of database class created locally
        val contentValues = ContentValues()
        contentValues.put(COL_2, name)
        contentValues.put(COL_3, surname)
        contentValues.put(COL_4, marks)
        val result = db.insert(TABLE_NAME, null, contentValues)//insert method return -1L in case of error and row value to which data is inserted in case of success
        return if (result == -1L) false else true//verification of whether data is inserted or not
    }

    val allData: Cursor//Cursor is an interface which provide random read write access to the result set returned by a database query.
        get() {
            val db = this.writableDatabase
            return db.rawQuery("select * from $TABLE_NAME", null)
        }

    fun updateData(id: String, name: String?, surname: String?, marks: String?): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_1, id)
        contentValues.put(COL_2, name)
        contentValues.put(COL_3, surname)
        contentValues.put(COL_4, marks)
        db.update(TABLE_NAME, contentValues, "ID = ?", arrayOf(id))
        return true
    }

    fun deleteData(id: String): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_NAME, "ID = ?", arrayOf(id))
    }

    companion object {
        const val DATABASE_NAME = "Student.db"
        const val TABLE_NAME = "student_table"
        const val COL_1 = "ID"
        const val COL_2 = "NAME"
        const val COL_3 = "SURNAME"
        const val COL_4 = "MARKS"
    }
}