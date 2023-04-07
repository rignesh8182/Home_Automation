package com.example.home_automation

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.home_automation.Fragments.HomeFragment
import com.example.home_automation.Fragments.NotificationFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    lateinit var bt_menu: BottomNavigationView
    lateinit var add_btn: FloatingActionButton

    lateinit var pro_btn: ImageView

    //Firebase implementation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bt_menu = findViewById(R.id.bottom_menu)
        add_btn = findViewById(R.id.add)
        pro_btn = findViewById(R.id.pro)

        bt_menu.background = null
        supportFragmentManager.beginTransaction().replace(R.id.frame, HomeFragment()).commit()

        bt_menu.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_home -> {
                    supportFragmentManager.beginTransaction().replace(R.id.frame, HomeFragment(),"HomeFragment").addToBackStack(null).commit()
                    true
                }
                R.id.notification -> {
                    supportFragmentManager.beginTransaction().replace(R.id.frame, NotificationFragment()).commit()
                    true
                }
                else -> {
                    false
                }
            }
        }

        add_btn.setOnClickListener {
            startActivity(Intent(applicationContext, AddDevice::class.java))
        }

        pro_btn.setOnClickListener {
            startActivity(Intent(applicationContext, Profile_Activity::class.java))
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            val fragment_home: Fragment? = supportFragmentManager.findFragmentByTag("HomeFragment")
            if (fragment_home == null) {
//                supportFragmentManager.beginTransaction().replace(R.id.frame, HomeFragment()).commit()
                super.onBackPressed()
            } else {

            }
        }
    }
}