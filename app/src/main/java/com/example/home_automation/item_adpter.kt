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
        holder.item_img.setImageResource(R.drawable.fan)
        if (item_list.get(position).state == 1) {
            holder.item_sw.isChecked = true
        } else {
            holder.item_sw.isChecked = false
        }

        holder.item_sw.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                var db: FirebaseDatabase = FirebaseDatabase.getInstance()
                db.getReference("hello").get().addOnCompleteListener(OnCompleteListener {
                    var snapshot: DataSnapshot = it.getResult()
                    var key: String = ""
                    for (ss: DataSnapshot in snapshot.children) {
                        val contactModel: itemModel? = ss.getValue(itemModel::class.java)
                        if (contactModel!!.state == item_list.get(position).state) {
                            key = ss.key!!
                            if (!key.isEmpty()) {
                                db.getReference("hello").child(key).child("state").setValue(1)
                            }
                            break
                        }
                    }
                })
            }else{
                var db: FirebaseDatabase = FirebaseDatabase.getInstance()
                db.getReference("hello").get().addOnCompleteListener(OnCompleteListener {
                    var snapshot: DataSnapshot = it.getResult()
                    var key: String = ""
                    for (ss: DataSnapshot in snapshot.children) {
                        val contactModel: itemModel? = ss.getValue(itemModel::class.java)
                        if (contactModel!!.state == item_list.get(position).state) {
                            key = ss.key!!
                            if (!key.isEmpty()) {
                                db.getReference("hello").child(key).child("state").setValue(0)
                            }
                            break
                        }
                    }
                })
            }
        }
    }

    override fun getItemCount(): Int {
        return item_list.size
    }

    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var item_img: ImageView = itemView.findViewById(R.id.it_img)
        var item_sw: Switch = itemView.findViewById(R.id.item_sw)
    }
}