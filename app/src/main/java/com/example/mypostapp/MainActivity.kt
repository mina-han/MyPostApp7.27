package com.example.mypostapp

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.DropBoxManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    lateinit var dbManager: DBManager
    lateinit var sqlitedb: SQLiteDatabase
    lateinit var layout: LinearLayout

    lateinit var ftbtnRegist: FloatingActionButton

    lateinit var tvTitle: TextView
    lateinit var tvID: TextView

    lateinit var str_title: String
    lateinit var str_ID: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ftbtnRegist = findViewById(R.id.ftbtnRegist)

        ftbtnRegist.setOnClickListener {
            val intent = Intent(this, Posting::class.java)
            startActivity(intent)
        }

        dbManager = DBManager(this, "post", null, 1)
        sqlitedb = dbManager.readableDatabase

        layout = findViewById(R.id.postsmall)

        var cursor: Cursor
        cursor = sqlitedb.rawQuery("SELECT * FROM post;", null)

        var num: Int = 0
        while(cursor.moveToNext()) {
            var str_title = cursor.getString((cursor.getColumnIndex("title"))).toString()
            var str_id = cursor.getString((cursor.getColumnIndex("id"))).toString()

            var layout_item: LinearLayout = LinearLayout(this)
            layout_item.orientation = LinearLayout.VERTICAL
            layout_item.setPadding(20, 10, 20, 30)
            layout_item.id = num
            layout_item.setTag(str_title)

            var tvTitle: TextView = TextView(this)
            tvTitle.text = str_title
            tvTitle.textSize = 30F
            layout_item.addView(tvTitle)

            var tvID: TextView = TextView(this)
            tvID.text = str_id
            layout_item.addView(tvID)

            layout_item.setOnClickListener{
                var intent = Intent(this, PostLarge::class.java)
                intent.putExtra("intent_title", str_title)
                startActivity(intent)
            }
            layout.addView(layout_item)
            num++

        }
        cursor.close()
        sqlitedb.close()
        dbManager.close()
    }
}

