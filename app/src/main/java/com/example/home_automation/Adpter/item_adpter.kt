package com.example.home_automation.Adpter

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.home_automation.DBHelper
import com.example.home_automation.LoginActivity
import com.example.home_automation.Models.Notification_model
import com.example.home_automation.Models.User_model
import com.example.home_automation.Models.itemModel
import com.example.home_automation.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class item_adpter(var item_list: ArrayList<itemModel>, var context: FragmentActivity?) : RecyclerView.Adapter<item_adpter.viewHolder>() {

    val dbhelper=DBHelper(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        var view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_grid, parent, false)
        return viewHolder(view)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.item_name.text = item_list.get(position).name
        var storeimg =
            FirebaseStorage.getInstance().reference.child(item_list.get(position).img + ".png")
        Log.d("temp", storeimg.toString())
        var tempfile = File.createTempFile("temp", "png")
        var progressDialog= ProgressDialog(context)
        progressDialog.setMessage("Fetching Images")
        progressDialog.setCancelable(false)
        progressDialog.show()

        storeimg.getFile(tempfile).addOnSuccessListener(OnSuccessListener {
            var bitmap = BitmapFactory.decodeFile(tempfile.absolutePath)
            holder.item_img.setImageBitmap(bitmap)
            if (progressDialog.isShowing){
                progressDialog.dismiss()
            }
        }).addOnFailureListener(OnFailureListener {

        })
        if (item_list.get(position).state == true) {
            holder.item_switch.setImageResource(R.drawable.on_switch)
        } else {
            holder.item_switch.setImageResource(R.drawable.off_switch)
        }

        holder.item_switch.setOnClickListener {
            if (item_list.get(position).state == true) {
                var db: FirebaseDatabase = FirebaseDatabase.getInstance()
                db.getReference("Device_data").get().addOnCompleteListener(OnCompleteListener {
                    var snapshot: DataSnapshot = it.getResult()
                    var key: String = ""
                    for (ss: DataSnapshot in snapshot.children) {
                        val contactModel = ss.getValue(itemModel::class.java)
                        if (contactModel!!.time == item_list.get(position).time) {
                            key = ss.key!!
                            if (!key.isEmpty()) {
                                db.getReference("Device_data").child(key).child("state").setValue(false)
//                                db.getReference("Device_data").child(key).child("time").setValue(System.currentTimeMillis())
                                item_list.get(position).state = false
                                item_list.set(position, item_list.get(position))
                                var progressDialog= ProgressDialog(context)
                                progressDialog.setMessage("Starting....")
                                progressDialog.setCancelable(false)
                                progressDialog.show()
                                val db = Firebase.database
                                val ref = db.getReference("Notification_data")
                                ref.push().setValue(
                                    Notification_model(item_list.get(position).name,item_list.get(position).img,false,System.currentTimeMillis())
                                ).addOnCompleteListener{
                                    if (progressDialog.isShowing){
                                        progressDialog.dismiss()
                                    }
                                }
                            }
                            break
                        }
                    }
                })
            } else {
                var db: FirebaseDatabase = FirebaseDatabase.getInstance()
                db.getReference("Device_data").get().addOnCompleteListener(OnCompleteListener {
                    var snapshot: DataSnapshot = it.getResult()
                    var key: String = ""
                    for (ss: DataSnapshot in snapshot.children) {
                        val contactModel = ss.getValue(itemModel::class.java)
                        if (contactModel!!.time == item_list.get(position).time) {
                            key = ss.key!!
                            if (!key.isEmpty()) {
                                db.getReference("Device_data").child(key).child("state").setValue(true)
//                                db.getReference("Device_data").child(key).child("time")
//                                    .setValue(System.currentTimeMillis())
                                item_list.get(position).state = true
                                item_list.set(position, item_list.get(position));
                                var progressDialog= ProgressDialog(context)
                                progressDialog.setMessage("Starting....")
                                progressDialog.setCancelable(false)
                                progressDialog.show()
                                val db = Firebase.database
                                val ref = db.getReference("Notification_data")
                                ref.push().setValue(
                                    Notification_model(item_list.get(position).name,item_list.get(position).img,true,System.currentTimeMillis())
                                ).addOnCompleteListener{
                                    if (progressDialog.isShowing){
                                        progressDialog.dismiss()
                                    }
                                }
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
        var item_name: TextView = itemView.findViewById(R.id.it_name)
        var item_switch: ImageButton = itemView.findViewById(R.id.it_sw)
    }
}