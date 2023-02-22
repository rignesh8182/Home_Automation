package com.example.home_automation.Adpter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.home_automation.R

class NotificationAdpter : RecyclerView.Adapter<NotificationAdpter.viewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        var v=LayoutInflater.from(parent.context).inflate(R.layout.notification_item,parent,false)
        return viewHolder(v)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

}