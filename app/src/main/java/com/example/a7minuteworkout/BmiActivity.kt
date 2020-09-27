package com.example.a7minuteworkout

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_bmi.*
import java.math.BigDecimal
import java.math.RoundingMode

class BmiActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi)

        setSupportActionBar(toolbar_bmi_activity)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = "CALCULATE BMI"
        }
        toolbar_bmi_activity.setNavigationOnClickListener {
            onBackPressed()
        }
        bt_calculate.setOnClickListener(this)


        rg_units.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.rb_UsUnits) {
                makeUsVisible()

            } else {
                makeMetricVisible()
            }

        }
    }

    override fun onClick(v: View?) {

        if (rb_metricUnits.isChecked) {

            if (et_w.text.toString().isEmpty() || et_h.text.toString().isEmpty()) {


            } else {
                val h = et_h.text.toString().toFloat() / 100
                val w = et_w.text.toString().toFloat()
                val bmi = w / (h * h)
                val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN)    // encurta p 2 casas decimais
                tv_your_bmi.text = bmiValue.toString()
                calculateBmi(bmi)
            }
        } else
        {
            if (et_wUs.text.toString().isEmpty() || et_feet.text.toString().isEmpty() || et_inch.text.toString().isEmpty())
            {
                Toast.makeText(this, "Pleas enter valid values", Toast.LENGTH_SHORT).show()
            }
            else
            {
                val w = et_wUs.text.toString().toFloat()
                val inch = et_inch.text.toString()
                val feet = et_feet.text.toString()
                val heightValue= inch.toFloat() + feet.toFloat() * 12
                val bmi= 703 * ( w / (heightValue * heightValue))
                val bmiValue= BigDecimal(bmi.toDouble()).setScale(2,RoundingMode.HALF_EVEN)
                tv_your_bmi.text= bmiValue.toString()
                calculateBmi(bmi)
            }

        }

    }


    private fun calculateBmi(bmi:Float) {

        if (bmi <= 18.5) {
            tv_description.text = "Severely underweight"
        }
        if (bmi >= 18.6 && bmi <= 24.9) {
            tv_description.text = "Normal"

        }
        if (bmi >= 25.0 && bmi <= 29.9) {
            tv_description.text = " Light overweight"
        }
        if (bmi >= 30 && bmi <= 34.9) {
            tv_description.text = "Overweight nivel 1"
            tv_description.setTextColor(ContextCompat.getColor(this, R.color.yellow))
        }

        if (bmi >= 35.0 && bmi <= 39.9) {
            tv_description.text = "Overweight nivel 2"
            tv_description.setTextColor(Color.RED)
        }

        if (bmi > 40) {
            tv_description.text = "Overweight nivel 3"
            tv_description.setTextColor(Color.RED)
        }

    }

    private fun makeUsVisible() {
        til_1Us.visibility = View.VISIBLE
        til_2Us.visibility = View.VISIBLE
        til_3Us.visibility = View.VISIBLE
        til_1.visibility = View.INVISIBLE
        til_2.visibility = View.INVISIBLE
        et_wUs.text!!.clear()
        et_feet.text!!.clear()
        et_inch.text!!.clear()
        tv_description.text = ""
        tv_your_bmi.text = ""

    }

    private fun makeMetricVisible() {
        til_1Us.visibility = View.INVISIBLE
        til_2Us.visibility = View.INVISIBLE
        til_3Us.visibility = View.INVISIBLE
        til_1.visibility = View.VISIBLE
        til_2.visibility = View.VISIBLE
        et_w.text!!.clear()
        et_h.text!!.clear()
        tv_description.text = ""
        tv_your_bmi.text = ""
    }


}