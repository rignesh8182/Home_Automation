package com.example.home_automation.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.home_automation.R

class HomeFragment : Fragment() {

    lateinit var bed:LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootview = inflater.inflate(R.layout.fragment_home, container, false)

        bed=rootview.findViewById(R.id.hm_bed)
        bed.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.frame, BedroomFragment()).commit()
        }

        return rootview
    }
}