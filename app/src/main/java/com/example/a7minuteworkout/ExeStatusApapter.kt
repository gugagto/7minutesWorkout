package com.example.a7minuteworkout

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_exe_status.view.*

class ExeStatusApapter(val itens: ArrayList<ExeModel>, val context: Context) :RecyclerView.Adapter<ExeStatusApapter.ViewHolder>() {



    class ViewHolder(view: View) :RecyclerView.ViewHolder(view){

        val tvItem= view.tv_item

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_exe_status,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model:ExeModel = itens[position]
        holder.tvItem.text=model.getId().toString()
        if (model.getIsSelected())
        {
            holder.tvItem.setBackgroundResource(R.drawable.item_circular_color_background)
            //holder.tvItem.background=ContextCompat.getDrawable(R.drawable.item_circular_color_background)
           // holder.tvItem.setTextColor(Color.parseColor("#212121"))
        }
        if(model.getIsCompleted())
        {
            holder.tvItem.setBackgroundResource(R.drawable.item_circular_done_color_background)
        }
    }

    override fun getItemCount(): Int {
       return itens.count()
    }
}