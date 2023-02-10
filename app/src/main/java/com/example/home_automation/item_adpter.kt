package com.example.home_automation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Switch
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase

class item_adpter(var item_list: ArrayList<itemModel>) :
    RecyclerView.Adapter<item_adpter.viewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        var view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_grid, parent, false)
        return viewHolder(view)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return item_list.size
    }

    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }
}