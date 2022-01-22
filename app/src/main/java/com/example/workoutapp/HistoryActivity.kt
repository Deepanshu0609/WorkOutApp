package com.example.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workoutapp.databinding.ActivityHistoryBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {
     private var binding: ActivityHistoryBinding?=null
    private var historyAdapter:HistoryAdapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarHistoryActivity)
        if(supportActionBar!=null)
        {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title="HISTORY"
        }
         binding?.toolbarHistoryActivity?.setNavigationOnClickListener{
             onBackPressed()
         }
        val dao=(application as WorkOutApp).db.historydao()

        getAlldates(dao)

    }
    private fun getAlldates(dao:HistoryDao){
        lifecycleScope.launch{

            dao.fetchAlldates().collect {allcompleted->

                val dates=ArrayList<String>()

                if(allcompleted.isNotEmpty()) {
                    binding?.tvHistory?.visibility= View.VISIBLE
                    binding?.rvHistory?.visibility=View.VISIBLE
                    binding?.tvNoDataAvailable?.visibility=View.GONE

                    binding?.rvHistory?.layoutManager=LinearLayoutManager(this@HistoryActivity)
                    for (i in allcompleted) {
                    dates.add(i.date)
                    }
                    historyAdapter=HistoryAdapter(dates)
                    binding?.rvHistory?.adapter=historyAdapter

                }else{
                    binding?.tvHistory?.visibility= View.GONE
                    binding?.rvHistory?.visibility=View.GONE
                    binding?.tvNoDataAvailable?.visibility=View.VISIBLE
                }
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
      binding=null
    }
}