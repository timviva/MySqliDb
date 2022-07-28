package com.example.morningsqlitedatabase

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

var edtTextName:TextView ?=null
var edtTextIdNumber:TextView ?=null
var edtTextEmail:TextView ?=null
var buttonsave:Button?=null
var buttonView:Button ?=null
var buttonDelete:Button?=null

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        edtTextName=findViewById(R.id.MedtName)
        edtTextEmail=findViewById(R.id.MbtnEmail)
        edtTextIdNumber=findViewById(R.id.MedtPassword)
        buttonsave=findViewById(R.id.Mbtnsave)
        buttonView=findViewById(R.id.Mbtnsave)
        buttonDelete=findViewById(R.id.MbtnDelete)
//create the database
        var db = openOrCreateDatabase("votersDB.", Context.MODE_PRIVATE, null)
//        Create a table called users inside the voters DB
        db.execSQL("CREATE TABLE IF NOT EXISTS users(jina VARCHAR,arafa VARCHAR,arafa VARCHAR,kitambulisho VARCHAR)")
//        Set an onclick listener to button save to implement the saving
        buttonsave!!.setOnClickListener{
//            Receive the data from the user
            var username = edtTextName!!.text.toString()
            var userEmail = edtTextEmail!!.text.toString()
            var userId = edtTextIdNumber!!.text.toString()
            if (username.isEmpty()||userEmail.isEmpty()||userId.isEmpty()){
                displayMessage("EMPTY FIELDS","Please fill all inputs")

            }else{
                db.execSQL("INSERT INTO users VALUES('"+username+"','"+userEmail+"','"+userId+"')")
                displayMessage("SUCCESS!!!","Data saved successfully")
                clear()
            }
        }
      buttonView!!.setOnClickListener {

          var cursor=db.rawQuery("SELECT * FROM users",null)
         if (cursor.count==0){
             displayMessage("NO DATA!!","SORRY,the Database is empty")
         }else{
             var buffer=StringBuffer()
             while (cursor.moveToNext()){
                 buffer.append(cursor.getString(0)+"\n")
                 buffer.append(cursor.getString(1)+"\n")
                 buffer.append(cursor.getString(2)+"\n\n")
                 displayMessage("DATABASE RECORDS",buffer.toString())
             }
         }
      }
        buttonDelete!!.setOnClickListener {
            var idNumber= edtTextIdNumber!!.text.toString().trim()
            if(idNumber.isEmpty()){
                displayMessage("EMPTY FIELD!!","Please enter ID  no!!")
            }else{
                var cursor=db.rawQuery("SELECT*FROM users WHER kitambulisho='"+idNumber+"' " ,null)
                if (cursor.count==0){
                    displayMessage("NO RECORD","Record deleted succesfully!!!")
                    clear()

                }
            }

        }







    }
    fun displayMessage (title:String,ujumbe:String){
        val AlertDialog=AlertDialog.Builder(this)
        AlertDialog.setTitle(title)
        AlertDialog.setMessage(ujumbe)
        AlertDialog.create().show()
    }
    fun clear(){
        edtTextName!!.setText("")
        edtTextIdNumber!!.setText("")
        edtTextEmail!!.setText("")
    }
}