package com.example.workoutapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.workoutapp.databinding.HistoryRecyclerViewBinding

class HistoryAdapter(private val item:ArrayList<String>):RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
    class ViewHolder(binding: HistoryRecyclerViewBinding):RecyclerView.ViewHolder(binding.root){
        val llhistory_rv=binding.llrecyclerview
        val tvposition=binding.tvPosition
        val tvitem=binding.tvItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(HistoryRecyclerViewBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val date: String = item[position]
        holder.tvposition.text = (position + 1).toString()
        holder.tvitem.text = date
       if (position % 2 == 0) {
            holder.llhistory_rv.setBackgroundColor(Color.parseColor("#EBEBEB"))
        } else {
            holder.llhistory_rv.setBackgroundColor(Color.parseColor("#FFFFFF"))
        }
    }
    override fun getItemCount(): Int {
       return item.size
    }
}