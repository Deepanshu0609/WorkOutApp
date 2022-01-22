package com.example.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.workoutapp.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BmiActivity : AppCompatActivity() {
    private var binding: ActivityBmiBinding?=null
    companion object{
        private const val metric="METRIC VIEW"
        private const val us="US VIEW"
    }
    private var currentbtn: String= metric
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarBmi)
        if(supportActionBar!=null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title="Calculate BMI"
        }
        binding?.toolbarBmi?.setNavigationOnClickListener{
            onBackPressed()
        }
        binding?.btnCalculateUnits?.setOnClickListener{
           calculateunits(currentbtn)
        }
        binding?.rg?.setOnCheckedChangeListener{ _ ,checked:Int->
             if(checked==R.id.rbmetric)
             {  currentbtn= metric
                 binding?.USheightinfeet?.visibility=View.INVISIBLE
                 binding?.USheightininch?.visibility=View.INVISIBLE
                  binding?.tvweight?.hint="Weight(in Kg)"
                 binding?.tvheight?.visibility=View.VISIBLE
                 binding?.etMetricUnitWeight?.text!!.clear()
                 binding?.etMetricUnitHeight?.text!!.clear()
                 binding?.llDiplayBMIResult?.visibility = View.INVISIBLE

             }
            else
             {     currentbtn=us
                 binding?.USheightinfeet?.visibility=View.VISIBLE
                 binding?.USheightininch?.visibility=View.VISIBLE
                 binding?.tvweight?.hint="Weight(in Pounds)"
                 binding?.tvheight?.visibility=View.INVISIBLE
                 binding?.etMetricUnitWeight?.text!!.clear()
                 binding?.etUSUnitHeight?.text!!.clear()
                 binding?.etUSUnitHeightinch?.text!!.clear()
                 binding?.llDiplayBMIResult?.visibility = View.INVISIBLE

             }

        }

    }
    private fun calculateunits(currentbtn:String)
    { if(currentbtn== metric) {
        if (validateMetricUnits()) {
            val weight = binding?.etMetricUnitWeight?.text.toString().toFloat()
            val height: Float = binding?.etMetricUnitHeight?.text.toString().toFloat() / 100
            val bmi = weight / (height * height)
            showBMI(bmi)
        }
        else {
            Toast.makeText(this, "Enter Valid Values of Height or Weight", Toast.LENGTH_SHORT)
                .show()
        }
    }
        else{
         if(validateUSMetricUnits())
         {
             val feet=binding?.etUSUnitHeight?.text.toString().toFloat()
             val Inch=binding?.etUSUnitHeightinch?.text.toString().toFloat()
             val height=Inch+feet*12
             val weight = binding?.etMetricUnitWeight?.text.toString().toFloat()
             val bmi=703*(weight/(height*height))
             showBMI(bmi)
         }
    }
    }
    private fun showBMI(bmi:Float){
        val bmiLabel:String
        val bmiDescription:String

        if (bmi.compareTo(15f) <= 0) {
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0
        ) {
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops!You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0
        ) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0
        ) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        } else if (java.lang.Float.compare(bmi, 25f) > 0 && java.lang.Float.compare(
                bmi,
                30f
            ) <= 0
        ) {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0
        ) {
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0
        ) {
            bmiLabel = "Obese Class || (Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        } else {
            bmiLabel = "Obese Class ||| (Very Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }

        //Use to set the result layout visible
        val bmiValue=BigDecimal(bmi.toDouble()).setScale(2,RoundingMode.HALF_EVEN)
        binding?.llDiplayBMIResult?.visibility = View.VISIBLE
        binding?.tvBMIValue?.text=bmiValue.toString()
        binding?.tvBMIType?.text=bmiLabel
        binding?.tvBMIDescription?.text=bmiDescription
    }
    private fun validateMetricUnits():Boolean{
        var isValid=true
        if(binding?.etMetricUnitWeight?.text.toString().isEmpty())
            isValid=false
        else if(binding?.etMetricUnitHeight?.text.toString().isEmpty())
            isValid=false

        return isValid

    }
    private fun validateUSMetricUnits():Boolean{
        var isValid=true
        if(binding?.etMetricUnitWeight?.text.toString().isEmpty())
            isValid=false
        else if(binding?.etUSUnitHeight?.text.toString().isEmpty())
            isValid=false
        if(binding?.etUSUnitHeightinch?.text.toString().isEmpty())
            isValid=false

        return isValid

    }


}