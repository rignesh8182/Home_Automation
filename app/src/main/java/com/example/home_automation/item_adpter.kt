package com.example.home_automation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Switch
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class item_adpter(var item_list:ArrayList<itemModel>) : RecyclerView.Adapter<item_adpter.viewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        var view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_grid, parent, false)
        return viewHolder(view)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.item_img.setImageResource(item_list.get(position).img!!.toInt())
        if (item_list.get(position).state == 1){
            holder.item_sw.isChecked=true
        }else{
            holder.item_sw.isChecked=false
        }
    }

    override fun getItemCount(): Int {
        return item_list.size
    }

    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var item_img:ImageView=itemView.findViewById(R.id.it_img)
        var item_sw:Switch=itemView.findViewById(R.id.item_sw)
    }
}