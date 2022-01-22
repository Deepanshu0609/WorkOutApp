package com.example.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.lifecycleScope
import com.example.workoutapp.databinding.ActivityFinishBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class FinishActivity : AppCompatActivity() {
    var binding: ActivityFinishBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarFinishActivity)
        if(supportActionBar!=null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbarFinishActivity?.setNavigationOnClickListener{
            onBackPressed()
        }
        binding?.btnFinish?.setOnClickListener{
            finish()
        }
        val dao=(application as WorkOutApp).db.historydao()
        addDatetoDatabase(dao)
    }
    private fun addDatetoDatabase(dao:HistoryDao){
        val c=Calendar.getInstance()
        val dateTime=c.time
        val sdf=SimpleDateFormat("dd MMM yyyy HH:mm:ss",Locale.getDefault())
        val date=sdf.format(dateTime)
        lifecycleScope.launch {
              dao.insert(Historyentity(date))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
      binding=null
    }
}