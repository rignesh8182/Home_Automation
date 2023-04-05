package com.example.home_automation.Fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.database.Cursor
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.LEFT
import androidx.recyclerview.widget.ItemTouchHelper.RIGHT

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.home_automation.Adpter.NotificationAdpter
import com.example.home_automation.DBHelper
import com.example.home_automation.Models.Notification_model
import com.example.home_automation.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class NotificationFragment() : Fragment() {

    lateinit var notify_list: RecyclerView
    lateinit var dbHelper: DBHelper
    lateinit var del_all: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_notification, container, false)

        notify_list = rootView.findViewById(R.id.notify)
        dbHelper = DBHelper(context)
        del_all = rootView.findViewById(R.id.del_all)
        var item_list: ArrayList<Notification_model> = ArrayList()
        var progressDialog= ProgressDialog(context)
        progressDialog.setMessage("Fetching...")

        progressDialog.setCancelable(false)
        progressDialog.show()

        var db = FirebaseDatabase.getInstance().getReference("Notification_data")
        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var item_list: ArrayList<Notification_model> = ArrayList()
                for (ss in snapshot.children) {
                    item_list.add(ss.getValue(Notification_model::class.java)!!)
                }
                notify_list.layoutManager = LinearLayoutManager(activity)
                notify_list.adapter = NotificationAdpter(item_list, requireActivity())
                if (progressDialog.isShowing){
                    progressDialog.dismiss()
                }
                del_all.visibility = View.VISIBLE
            }

            override fun onCancelled(error: DatabaseError) {}
        })
        val db_ref=FirebaseDatabase.getInstance()
        val itemTouchHelperc = object : ItemTouchHelper.SimpleCallback(0, LEFT or RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val pos = viewHolder.adapterPosition
                var time = item_list.get(pos).time

                var progressDialog= ProgressDialog(context)
                progressDialog.setMessage("Deleting...")
                progressDialog.setCancelable(false)
                progressDialog.show()
                db_ref.getReference("Notification_data").get().addOnCompleteListener {
                    var snapshot:DataSnapshot=it.getResult()
                    var key:String=""
                    for (ss in snapshot.children){
                        var temp_list=ss.getValue(Notification_model::class.java)
                        if (temp_list!!.time!!.equals(time)){
                            key= ss.key.toString()
                            break
                        }
                    }

                    if (!key.isEmpty()){
                        db_ref.getReference("Notification_data").child(key).removeValue().addOnCompleteListener {
                            if (progressDialog.isShowing){
                                progressDialog.dismiss()
                            }
                        }
                    }
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperc)
        itemTouchHelper.attachToRecyclerView(notify_list)

        val builder = AlertDialog.Builder(activity)
        del_all.setOnClickListener {
            builder.setTitle("Are you sure..?")
            builder.setMessage("All the history will be deleted")
            builder.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
                var progressDialog= ProgressDialog(context)
                progressDialog.setMessage("Deleting...")
                progressDialog.setCancelable(false)
                progressDialog.show()
                db_ref.getReference("Notification_data").removeValue().addOnCompleteListener {
                    if (progressDialog.isShowing){
                        progressDialog.dismiss()
                    }
                }
                item_list.clear()
                notify_list.adapter = NotificationAdpter(item_list, requireActivity())
                Toast.makeText(context, "All notification history deleted", Toast.LENGTH_SHORT)
                    .show()
            })
            builder.setNegativeButton("No", DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()
            })
            builder.show()
        }

        return rootView
    }
}