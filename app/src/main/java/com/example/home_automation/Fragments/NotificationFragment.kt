package com.example.home_automation.Fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.database.Cursor
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


        val builder = AlertDialog.Builder(activity)

//        var db= FirebaseDatabase.getInstance().getReference("Hello")
//        db.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                var item_list:ArrayList<Notification_model> = ArrayList()
//                for (ss in snapshot.children) {
//                    item_list.add(ss.getValue(Notification_model::class.java)!!)
//                }
//                notify_list.layoutManager=LinearLayoutManager(activity)
//                notify_list.adapter=NotificationAdpter(item_list)
//            }
//
//            override fun onCancelled(error: DatabaseError) {}
//        })
        var data: Cursor = dbHelper.getAll()
        data.moveToLast()
        item_list.add(
            Notification_model(
                data.getInt(0),
                data.getString(1),
                data.getString(3),
                data.getInt(2) == 1,
                data.getLong(4)
            )
        )
        while (data.moveToPrevious()) {
            item_list.add(
                Notification_model(
                    data.getInt(0),
                    data.getString(1),
                    data.getString(3),
                    data.getInt(2) == 1,
                    data.getLong(4)
                )
            )
        }
        notify_list.layoutManager = LinearLayoutManager(activity)
        notify_list.adapter = NotificationAdpter(item_list, requireActivity())

        val itemTouchHelperc = object : ItemTouchHelper.SimpleCallback(0, LEFT or RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val pos = viewHolder.adapterPosition
                var id = item_list.get(pos).id
                val db = DBHelper(context)
                var res = db.del_item(id!!)
                item_list.removeAt(pos)
                notify_list.adapter = NotificationAdpter(item_list, requireActivity())
                if (res == 1) {
                    Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperc)
        itemTouchHelper.attachToRecyclerView(notify_list)

        del_all.setOnClickListener {

            builder.setTitle("Are you sure..?")
            builder.setMessage("All the history will be deleted")
            builder.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
                dbHelper.del_all()
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