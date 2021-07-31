package com.example.mypostapp

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.strictmode.SqliteObjectLeakedViolation
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.time.LocalDate

//데이터 베이스에 글 저장
class PostLarge : AppCompatActivity() {

    lateinit var dbManager: DBManager
    lateinit var sqlitedb:SQLiteDatabase

    lateinit var btnGood: Button
    lateinit var goodnumber : TextView

    lateinit var tvTitle: TextView
    lateinit var tvContents: TextView
    lateinit var tvDate: TextView

    lateinit var str_title: String
    lateinit var str_contents: String
    lateinit var str_date: String //날짜 아직 구현X

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_large)

        var count = 0;

        btnGood = findViewById(R.id.btnGood)
        goodnumber = findViewById(R.id.goodnumber)

        btnGood.setOnClickListener {
            if(count < 1){
                count+=1;
                goodnumber.text = count.toString();
            } else{
                Toast.makeText(this, "공감은 한 번만 할 수 있습니다.", Toast.LENGTH_SHORT).show();
            }
        }

        //tvDate = findViewById(R.id.date)
        tvTitle = findViewById(R.id.edttitle)
        tvContents = findViewById(R.id.edtcontents)

        val intent = intent
        str_title = intent.getStringExtra("intent_title").toString()

        dbManager = DBManager(this, "post", null, 1)
        sqlitedb = dbManager.readableDatabase

        var cursor: Cursor
        cursor = sqlitedb.rawQuery("SELECT * FROM post WHERE title = '" + str_title + "';", null)
        if (cursor.moveToNext()) {
            str_contents = cursor.getString((cursor.getColumnIndex("contents"))).toString()
        }

        cursor.close()
        sqlitedb.close()
        dbManager.close()

        tvTitle.text = str_title
        tvContents.text = str_contents+"\n"

    }

    //목록 단추
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
       return true
    }

    //글 삭제
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
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