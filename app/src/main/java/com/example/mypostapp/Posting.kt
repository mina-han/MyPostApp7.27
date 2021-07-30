package com.example.mypostapp

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.DropBoxManager
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup

class Posting : AppCompatActivity() {

    lateinit var dbManager: DBManager
    lateinit var sqlitedb: SQLiteDatabase

    lateinit var btnButton: Button
    lateinit var edtTitle: EditText
    lateinit var edtContents: EditText
    lateinit var rg_ID: RadioGroup
    lateinit var rg_noname: RadioButton
    lateinit var rg_yesname: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_postinfo)

        btnButton = findViewById(R.id.btnButton)
        edtTitle = findViewById(R.id.edttitle)
        edtContents = findViewById(R.id.edtcontents)
        rg_ID = findViewById(R.id.ID)
        rg_noname = findViewById(R.id.noname)
        rg_yesname = findViewById(R.id.yesname)

        dbManager = DBManager(this, "post", null, 1)

        btnButton.setOnClickListener {
            var str_title: String = edtTitle.text.toString()
            var str_contents: String = edtContents.text.toString()
            var str_id: String = ""

            if (rg_ID.checkedRadioButtonId == R.id.noname) {
                str_id = rg_noname.text.toString()
            }
            if (rg_ID.checkedRadioButtonId == R.id.yesname) {
                str_id = rg_yesname.text.toString()
            }

                sqlitedb = dbManager.writableDatabase
                sqlitedb.execSQL("INSERT INTO post VALUES ('"+ str_title +"','"+ str_id +"','"+ str_contents +"');")
                sqlitedb.close()

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("intent_title", str_title);
            startActivity(intent)
        }


    }
}