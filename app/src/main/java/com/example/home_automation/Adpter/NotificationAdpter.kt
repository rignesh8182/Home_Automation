package com.example.home_automation.Adpter

import android.graphics.BitmapFactory
import android.opengl.Visibility
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.home_automation.Models.Notification_model
import com.example.home_automation.R
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import java.text.SimpleDateFormat

class NotificationAdpter(var notify_list:ArrayList<Notification_model>) : RecyclerView.Adapter<NotificationAdpter.viewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        var v=LayoutInflater.from(parent.context).inflate(R.layout.notification_item,parent,false)
        return viewHolder(v)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        var storeimg =
            FirebaseStorage.getInstance().reference.child(notify_list.get(position).img + ".png")
        Log.d("temp", storeimg.toString())
        var tempfile = File.createTempFile("temp", "png")
        storeimg.getFile(tempfile).addOnSuccessListener(OnSuccessListener {
            var bitmap = BitmapFactory.decodeFile(tempfile.absolutePath)
            holder.img.setImageBitmap(bitmap)

        }).addOnFailureListener(OnFailureListener {

        })

        var formatter=SimpleDateFormat("hh:mm / dd-MM-yy")
        holder.time.text=formatter.format(notify_list.get(position).time)
        if (notify_list.get(position).state == true){
            holder.msg.text="Bedroom’s ${notify_list.get(position).name} turned on"
        }else{
            holder.msg.text="Bedroom’s ${notify_list.get(position).name} turned off"
        }

        holder.itemView.setOnLongClickListener {
            holder.check.visibility=View.VISIBLE
            true
        }
    }

    override fun getItemCount(): Int {
        return  notify_list.size
    }

    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var img:ImageView=itemView.findViewById(R.id.notify_img)
        var msg:TextView=itemView.findViewById(R.id.notify_msg)
        var time:TextView=itemView.findViewById(R.id.notify_time)
        var check:CheckBox=itemView.findViewById(R.id.check_item)


    }

}