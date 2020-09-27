package com.example.a7minuteworkout

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.util.Log.d
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_exercise.*
import kotlinx.android.synthetic.main.dialog_back_confirmation.*
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(),TextToSpeech.OnInitListener {


    private var restTimer: CountDownTimer? = null
    private var restProgress = 0

    private var exeTimer: CountDownTimer? = null
    private var exeProgress = 0

    private var exerciseList:ArrayList<ExeModel>?=null
    private var currentExePosition=-1

    private var tts:TextToSpeech?=null

    private var player: MediaPlayer?=null

    private var exerciseAdapter:ExeStatusApapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        setSupportActionBar(toolbar_exercise)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
        toolbar_exercise.setNavigationOnClickListener {
           //onBackPressed()               volta pra tela anterior
            customDialog()
        }

        tts= TextToSpeech(this,this)

        exerciseList=ExerciseConstansts.defaultExercise()
        setupRestView()


        rv_exe_status.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        exerciseAdapter= ExeStatusApapter(exerciseList!!,this)
        rv_exe_status.adapter=exerciseAdapter
    }

    private fun setupExeView() {

        ll_restview.visibility = View.INVISIBLE
        ll_exe_view.visibility = View.VISIBLE

        if (exeTimer != null) {
            exeTimer!!.cancel()
            exeProgress = 0

        }

        speakOut(exerciseList!![currentExePosition].getName())
        setExeProgressBar()
        image.setImageResource(exerciseList!![currentExePosition].getImage())
       tv_exe_name.text=exerciseList!![currentExePosition].getName()

    }

    private fun setExeProgressBar() {

        progress_bar_exercise.progress = restProgress
        exeTimer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                exeProgress++
                progress_bar_exercise.progress = 30 - exeProgress
                tv_timer_exercise.text = (30 - exeProgress).toString()

            }

            override fun onFinish() {
                if (currentExePosition<11)
                {
                    exerciseList!![currentExePosition].setIsSelected(false)
                    exerciseList!![currentExePosition].setIsCompleted(true)
                    exerciseAdapter!!.notifyDataSetChanged()

                    setupRestView()
                }
                else
                {
                    finish()
                    startActivity(Intent(this@ExerciseActivity,FinishActivity::class.java))
                    finish()
                }

            }
        }.start()


    }

    private fun setRestProgressBar() {
        progress_bar.progress = restProgress
        restTimer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                progress_bar.progress = 10 - restProgress

                tv_timer.text = (11 - restProgress).toString()
                speakOut(tv_timer.text.toString())

            }

            override fun onFinish() {

                currentExePosition++
                exerciseList!![currentExePosition].setIsSelected(true)
                exerciseAdapter!!.notifyDataSetChanged()
                    setupExeView()


            }


        }.start()


    }

    private fun setupRestView() {

        player= MediaPlayer.create(applicationContext,R.raw.press_start)
        player!!.isLooping = false               //para somente tocar 1 vez
        player!!.start()

        ll_restview.visibility=View.VISIBLE
        ll_exe_view.visibility= View.INVISIBLE

        if (restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }

        tv_next_exe.text=exerciseList!![currentExePosition+1].getName()
        setRestProgressBar()

    }

    override fun onDestroy() {

        if (exeTimer!=null)
       {
           exeTimer!!.cancel()
           exeProgress=0
       }

        if (restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }

        if (tts!=null)
        {
            tts!!.stop()
            tts!!.shutdown()
        }

        if (player!=null)
        {
            player!!.stop()
        }
        super.onDestroy()
    }

    override fun onInit(status: Int) {

        if (status==TextToSpeech.SUCCESS)                  //verifica se funciona no device
        {
            val result=tts!!.setLanguage(Locale.US)
            if (result==TextToSpeech.LANG_MISSING_DATA || result==TextToSpeech.LANG_NOT_SUPPORTED)
            {
                Log.e("tts","Language not supported!")
            }
        }else
        {
            Log.e("tts","Error!")
        }

    }

   private fun customDialog()
   {
       val customDialog=Dialog(this)
       customDialog.setContentView(R.layout.dialog_back_confirmation)
       customDialog.bt_yes.setOnClickListener {
           finish()
           customDialog.dismiss()
       }
       customDialog.bt_no.setOnClickListener {
           customDialog.dismiss()
       }
        customDialog.show()


   }



    private fun speakOut(text:String)
    {
        tts!!.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")
    }

}