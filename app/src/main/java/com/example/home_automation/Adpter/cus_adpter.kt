package com.example.home_automation.Adpter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.home_automation.R

class cus_adpter(val context:Context, val cus_title:Array<String>, val cus_img: IntArray) : BaseAdapter() {
    override fun getCount(): Int {
        return cus_title.size
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view=LayoutInflater.from(context).inflate(R.layout.custom_layout,parent,false)
        val img:ImageView=view.findViewById(R.id.img)
        val title:TextView=view.findViewById(R.id.title)
        img.setImageResource(cus_img[position])
        title.text=cus_title[position]
        return view
    }
}