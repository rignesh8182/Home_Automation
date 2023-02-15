package com.example.home_automation

import android.app.ProgressDialog
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class item_adpter(var item_list: ArrayList<itemModel>, var context: FragmentActivity?) :
    RecyclerView.Adapter<item_adpter.viewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        var view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_grid, parent, false)
        return viewHolder(view)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.item_name.text = item_list.get(position).name
        var storeimg = FirebaseStorage.getInstance().reference.child(item_list.get(position).img + ".png")
        Log.d("temp",storeimg.toString())
        var tempfile = File.createTempFile("temp", "png")
        storeimg.getFile(tempfile).addOnSuccessListener(OnSuccessListener {
            var bitmap = BitmapFactory.decodeFile(tempfile.absolutePath)
            holder.item_img.setImageBitmap(bitmap)

        }).addOnFailureListener(OnFailureListener {

        })
        if (item_list.get(position).state == true) {
            holder.item_switch.setImageResource(R.drawable.ic_switch_on)
        } else {
            holder.item_switch.setImageResource(R.drawable.ic_switch_off)
        }

        holder.item_switch.setOnClickListener {
            if (item_list.get(position).state == true){
                holder.item_switch.setImageResource(R.drawable.ic_switch_off)
                var db: FirebaseDatabase = FirebaseDatabase.getInstance()
                db.getReference("Hello").get().addOnCompleteListener(OnCompleteListener {
                    var snapshot: DataSnapshot = it.getResult()
                    var key: String = ""
                    for (ss: DataSnapshot in snapshot.children) {
                        val contactModel= ss.getValue(itemModel::class.java)
                        if (contactModel!!.name == item_list.get(position).name) {
                            key = ss.key!!
                            if (!key.isEmpty()) {
                                db.getReference("Hello").child(key).child("state").setValue(false)
                                item_list.get(position).state=false
                                item_list.set(position,item_list.get(position));
                                notifyItemChanged(position)
                            }
                            break
                        }
                    }
                })
            }else{
                holder.item_switch.setImageResource(R.drawable.ic_switch_on)
                var db: FirebaseDatabase = FirebaseDatabase.getInstance()
                db.getReference("Hello").get().addOnCompleteListener(OnCompleteListener {
                    var snapshot: DataSnapshot = it.getResult()
                    var key: String = ""
                    for (ss: DataSnapshot in snapshot.children) {
                        val contactModel = ss.getValue(itemModel::class.java)
                        if (contactModel!!.name == item_list.get(position).name) {
                            key = ss.key!!
                            if (!key.isEmpty()) {
                                db.getReference("Hello").child(key).child("state").setValue(true)
                                item_list.get(position).state=true
                                item_list.set(position,item_list.get(position));
                                notifyItemChanged(position)
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