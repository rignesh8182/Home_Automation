package com.example.home_automation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.home_automation.Fragments.HomeFragment
import com.example.home_automation.Fragments.NotificationFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

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
                R.id.setting->{
                    var sp=getSharedPreferences("User_data", MODE_PRIVATE)
                    val ed=sp.edit()
                    ed.putString("user_mail","")
                    ed.putString("user_pass","")
                    ed.commit()
                    Toast.makeText(this, "Logout successful", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(applicationContext,LoginActivity::class.java))
                    finish()
                    true
                }
                else -> {
                    false
                }
            }
        }

        add_btn.setOnClickListener {
            startActivity(Intent(applicationContext,AddDevice::class.java))
        }
    }
}