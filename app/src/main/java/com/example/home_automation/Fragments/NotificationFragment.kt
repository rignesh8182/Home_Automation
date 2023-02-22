package com.example.home_automation.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.home_automation.Models.itemModel
import com.example.home_automation.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class NotificationFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView=inflater.inflate(R.layout.fragment_notification, container, false)

        var db= FirebaseDatabase.getInstance().getReference("Hello")
        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var item_list:ArrayList<itemModel> = ArrayList()
                for (ss in snapshot.children) {
                    item_list.add(ss.getValue(itemModel::class.java)!!)
                }


            }

            override fun onCancelled(error: DatabaseError) {}
        })

        return rootView
    }

}