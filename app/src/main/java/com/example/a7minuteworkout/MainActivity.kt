package com.example.a7minuteworkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ll_start.setOnClickListener {

            val intent = Intent(this, ExerciseActivity::class.java)
            startActivity(intent)
        }

            bt_bmi.setOnClickListener {

                val intent = Intent(this, BmiActivity::class.java)
                startActivity(intent)

            }


        bt_history.setOnClickListener {
            startActivity(Intent(this,HistoryActivity::class.java))
        }


    }


}