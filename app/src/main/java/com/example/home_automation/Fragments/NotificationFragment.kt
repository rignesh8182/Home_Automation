package com.example.home_automation.Fragments

import android.annotation.SuppressLint
import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.home_automation.Adpter.NotificationAdpter
import com.example.home_automation.DBHelper
import com.example.home_automation.Models.Notification_model
import com.example.home_automation.R


class NotificationFragment : Fragment() {

    lateinit var notify_list:RecyclerView
    lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView=inflater.inflate(R.layout.fragment_notification, container, false)

        notify_list=rootView.findViewById(R.id.notify)
        dbHelper= DBHelper(context)
        var item_list:ArrayList<Notification_model> = ArrayList()

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
        var data:Cursor=dbHelper.getAll()
        while (data.moveToNext()){
            item_list.add(Notification_model(data.getString(1),data.getString(3), data.getInt(2) == 1,data.getLong(4)))
        }
        notify_list.layoutManager= LinearLayoutManager(activity)
        notify_list.adapter= NotificationAdpter(item_list)

        return rootView
    }

}