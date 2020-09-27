package com.example.a7minuteworkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_finish.*
import java.text.SimpleDateFormat
import java.util.*

class FinishActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish)

       setSupportActionBar(tool_bar_finish_activity)
        val actionBar = supportActionBar
        if (actionBar!=null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true)       //coloca o botao de voltar
        }

        tool_bar_finish_activity.setNavigationOnClickListener {
            onBackPressed()                                        // funcao padrao para voltar
        }

        bt_finish.setOnClickListener {
            finish()
            startActivity(Intent(this,MainActivity::class.java))

        }


        addDateToDb()
    }

private  fun addDateToDb()
{
   val calendar=Calendar.getInstance()
    val time = calendar.time

    Log.i("date","$time")
    val simple= SimpleDateFormat("dd MMM yyyy  HH:mm:ss")
    val format = simple.format(time)
    val dbHandler =SqliteOpenHelper(this,null)
    dbHandler.addDate(format)
    Log.i("date","added")
}




}