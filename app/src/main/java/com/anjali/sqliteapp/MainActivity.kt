package com.anjali.sqliteapp

import android.app.AlertDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity :AppCompatActivity(){
    var myDb: DatabaseHelper? = null
    var editName: EditText? = null
    var editSurname: EditText? = null
    var editMarks: EditText? = null
    var editTextId: EditText? = null
    var btnAddData: Button? = null
    var btnviewAll: Button? = null
    var btnDelete: Button? = null
    var btnviewUpdate: Button? = null

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myDb = DatabaseHelper(this)//instance of DatabaseHelper class using which database and table is created

        editName = findViewById(R.id.editText_name) as EditText?
        editSurname = findViewById(R.id.editText_surname) as EditText?
        editMarks = findViewById(R.id.editText_Marks) as EditText?
        editTextId = findViewById(R.id.editText_id) as EditText?
        btnAddData = findViewById(R.id.button_add) as Button?
        btnviewAll = findViewById(R.id.button_viewAll) as Button?
        btnviewUpdate = findViewById(R.id.button_update) as Button?
        btnDelete = findViewById(R.id.button_delete) as Button?
        AddData()
        viewAll()
        UpdateData()
        DeleteData()
    }

    fun DeleteData() {
        btnDelete!!.setOnClickListener {
            val deletedRows = myDb!!.deleteData(editTextId!!.text.toString())
            if (deletedRows > 0) Toast.makeText(this@MainActivity, "Data Deleted", Toast.LENGTH_LONG).show() else Toast.makeText(this@MainActivity, "Data not Deleted", Toast.LENGTH_LONG).show()
            editTextId!!.setText("");
        }
    }

    fun UpdateData() {
        btnviewUpdate!!.setOnClickListener {
            val isUpdate = myDb!!.updateData(editTextId!!.text.toString(),
                    editName!!.text.toString(),
                    editSurname!!.text.toString(), editMarks!!.text.toString())
            if (isUpdate == true) Toast.makeText(this@MainActivity, "Data Updated", Toast.LENGTH_LONG).show() else Toast.makeText(this@MainActivity, "Data not Updated", Toast.LENGTH_LONG).show()

            restoreField()
        }
    }

    private fun restoreField() {
        editTextId?.setText("")
        editMarks?.setText("")
        editName?.setText("")
        editSurname?.setText("")
    }

    fun AddData() {
        btnAddData!!.setOnClickListener {
            val isInserted = myDb!!.insertData(editName!!.text.toString(),
                    editSurname!!.text.toString(),
                    editMarks!!.text.toString())
            if (isInserted == true) Toast.makeText(this@MainActivity, "Data Inserted", Toast.LENGTH_LONG).show() else Toast.makeText(this@MainActivity, "Data not Inserted", Toast.LENGTH_LONG).show()
            restoreField()
        }
    }

    fun viewAll() {
        btnviewAll!!.setOnClickListener(
                View.OnClickListener {
                    val res = myDb!!.allData
                    if (res.count == 0) {
                        // show message
                        showMessage("Error", "Nothing found")
                        return@OnClickListener
                    }
                    //creating instance of StringBuffer and getting all data one by one
                    val buffer = StringBuffer()
                    while (res.moveToNext()) { //Move the cursor to the previous row.This method will return false if the cursor is already before the  first entry in the result set.

                        buffer.append("""Id :${res.getString(0)} """.trimIndent()+"\n")//Retrieves the requested column text and stores it in the buffer provided.
                        buffer.append("""Name :${res.getString(1)}""".trimIndent()+"\n")
                        buffer.append("""Surname :${res.getString(2)}""".trimIndent()+"\n")
                        buffer.append("""Marks :${res.getString(3)}""".trimIndent()+"\n")

                        println()
                    }

                    // Show all data
                    showMessage("Data", buffer.toString())
                }
        )
    }

    fun showMessage(title: String?, Message: String?) {
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(true)
        builder.setTitle(title)
        builder.setMessage(Message)
        builder.show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)
    }
}