package com.example.a7minuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        setSupportActionBar(history_toolbar)
        val actionBar= supportActionBar
        if (actionBar!=null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title="EXERCISE COMPLETED"
        }

        history_toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        getAllDates()
    }

    private  fun getAllDates()
    {
         val dbHandler= SqliteOpenHelper(this,null)
        val list = dbHandler.gelAll()


        if (list.size > 0)
        {
           rvHistory.visibility= View.VISIBLE
            tvNoDataAvailble.visibility=View.GONE
            rvHistory.layoutManager = LinearLayoutManager(this)
            rvHistory.adapter=HistoryAdapter(this,list)
        }
        else
        {
            rvHistory.visibility= View.GONE
            tvNoDataAvailble.visibility=View.VISIBLE
        }


    }



}