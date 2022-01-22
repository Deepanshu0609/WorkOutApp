package com.example.workoutapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.workoutapp.databinding.RecyclerItemBinding

class ExerciseStatusAdapter(val items: ArrayList<exercise>):RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolder>() {

     class ViewHolder(binding: RecyclerItemBinding):RecyclerView.ViewHolder(binding.root){
         val  recyclerViewItem=binding.recyclerViewItem
     }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       return ViewHolder(RecyclerItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
           val model:exercise=items[position]
           holder.recyclerViewItem.text=items[position].getid().toString()
          when {
              model.getisselected() -> {
                  holder.recyclerViewItem.background =ContextCompat.getDrawable(holder.itemView.context,R.drawable.selected_item)
              }
              model.getisCompleted()->{
                  holder.recyclerViewItem.background=ContextCompat.getDrawable(holder.itemView.context,R.drawable.circular_green_bckg)
                  holder.recyclerViewItem.setTextColor(Color.parseColor("#FFFFFF"))
              }
            else->{
                  holder.recyclerViewItem.background=ContextCompat.getDrawable(holder.itemView.context,R.drawable.recyclerview_item_bckg)
            }
          }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}