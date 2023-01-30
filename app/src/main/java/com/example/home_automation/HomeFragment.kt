package com.example.home_automation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {

    lateinit var item_list: ArrayList<itemModel>
    lateinit var recycle: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootview= inflater.inflate(R.layout.fragment_home, container, false)
        item_list = ArrayList()
        recycle=rootview.findViewById(R.id.item_sh)

        val db = Firebase.database
        val myref = db.getReference("hello")

        myref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot != null) {
                    try {
                        item_list = snapshot.children.map { ss ->
                            ss.getValue(itemModel::class.java)!!
                        } as ArrayList<itemModel>
                        recycle.layoutManager=StaggeredGridLayoutManager(2,LinearLayout.VERTICAL)
                        recycle.adapter=item_adpter(item_list)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        return rootview
    }
}