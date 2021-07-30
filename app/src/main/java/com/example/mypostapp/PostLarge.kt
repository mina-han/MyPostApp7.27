package com.example.mypostapp

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.strictmode.SqliteObjectLeakedViolation
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import org.w3c.dom.Text

class PostLarge : AppCompatActivity() {

    lateinit var dbManager: DBManager
    lateinit var sqlitedb:SQLiteDatabase

    lateinit var tvTitle: TextView
    lateinit var tvContents: TextView
    lateinit var tvID: TextView

    lateinit var str_title: String
    lateinit var str_contents: String
    lateinit var str_ID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_large)

        tvTitle = findViewById(R.id.edttitle)
        tvContents = findViewById(R.id.edtcontents)
        tvID = findViewById(R.id.ID)

        val intent = intent
        str_title = intent.getStringExtra("intent_title").toString()

        dbManager = DBManager(this,"post", null, 1)
        sqlitedb = dbManager.readableDatabase

        var cursor: Cursor
        cursor = sqlitedb.rawQuery("SELECT * FROM post WHERE title = '"+str_title+"';", null)

        if(cursor.moveToNext()) {
            str_ID = cursor.getString((cursor.getColumnIndex("id"))).toString()
            str_contents = cursor.getString((cursor.getColumnIndex("contents"))).toString()
        }

        cursor.close()
        sqlitedb.close()
        dbManager.close()

        tvTitle.text = str_title
        tvContents.text = str_contents
        tvID.text = str_ID + "\n"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
       return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId) {
            R.id.action_remove -> {
                dbManager = DBManager(this,"post", null, 1)
                sqlitedb = dbManager.readableDatabase
                sqlitedb.execSQL("DELETE FROM post WHERE title = '"+ str_title +"';")
                sqlitedb.close()
                dbManager.close()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}