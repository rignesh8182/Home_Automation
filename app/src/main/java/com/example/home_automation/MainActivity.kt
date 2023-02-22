package com.example.home_automation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    lateinit var bt_menu: BottomNavigationView
    lateinit var add_btn: FloatingActionButton

    //Firebase implementation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bt_menu = findViewById(R.id.bottom_menu)
        add_btn = findViewById(R.id.add)

        bt_menu.background = null
        supportFragmentManager.beginTransaction().replace(R.id.frame, HomeFragment()).commit()

        bt_menu.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_home -> {
                    supportFragmentManager.beginTransaction().replace(R.id.frame, HomeFragment()).commit()
                    true
                }
                R.id.notification->{
                    supportFragmentManager.beginTransaction().replace(R.id.frame, NotificationFragment()).commit()
                    true
                }
                else -> {
                    false
                }
            }
        }
    }
}