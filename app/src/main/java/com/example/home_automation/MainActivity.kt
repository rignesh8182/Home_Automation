package com.example.home_automation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var bt_menu:BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bt_menu=findViewById(R.id.bottom_menu)
        bt_menu.background=null

        supportFragmentManager.beginTransaction().replace(R.id.frame,HomeFragment()).commit()
    }
}