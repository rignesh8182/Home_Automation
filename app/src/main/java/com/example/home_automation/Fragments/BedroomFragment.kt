package com.example.home_automation.Fragments

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.home_automation.Adpter.item_adpter
import com.example.home_automation.Models.itemModel
import com.example.home_automation.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class BedroomFragment : Fragment() {

    lateinit var items: ViewPager2


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var progressDialog=ProgressDialog(activity)
        progressDialog.setMessage("Fetching Appliances")
        progressDialog.setCancelable(false)
        progressDialog.show()
        val rootView=inflater.inflate(R.layout.fragment_bedroom, container, false)
        var db=FirebaseDatabase.getInstance().getReference("Hello")
        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var item_list:ArrayList<itemModel> = ArrayList()
                for (ss in snapshot.children) {
                    
                    item_list.add(ss.getValue(itemModel::class.java)!!)
                }

                items=rootView.findViewById(R.id.items)
                items.adapter= item_adpter(item_list,activity)
                items.setPadding(40,0,40,0)
                items.offscreenPageLimit=3
                items.getChildAt(0).overScrollMode=RecyclerView.OVER_SCROLL_NEVER

                val compositePageTransformer = CompositePageTransformer()
                compositePageTransformer.addTransformer(MarginPageTransformer(40))
                compositePageTransformer.addTransformer { page, position ->
                    val r = 1 - Math.abs(position)
                    page.scaleY = 0.85f + r * 0.15f
                }

                items.setPageTransformer(compositePageTransformer)

                if (progressDialog.isShowing){
                    progressDialog.dismiss()
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })

        return rootView
    }
}