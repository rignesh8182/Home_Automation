package com.example.home_automation

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class BedroomFragment : Fragment() {

    lateinit var items:ViewPager2


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView=inflater.inflate(R.layout.fragment_bedroom, container, false)
        var db=FirebaseDatabase.getInstance().getReference("Hello")
        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var item_list:ArrayList<itemModel> = ArrayList()
                for (ss in snapshot.children) {
                    item_list.add(ss.getValue(itemModel::class.java)!!)
                }

                items=rootView.findViewById(R.id.items)
                items.adapter=item_adpter(item_list,activity)
                items.offscreenPageLimit=3
                items.clipToPadding=false
                items.clipChildren=false
                items.getChildAt(0).overScrollMode=RecyclerView.OVER_SCROLL_NEVER
            }

            override fun onCancelled(error: DatabaseError) {}
        })

        return rootView
    }
}