package com.example.home_automation

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

class Profile_Activity : AppCompatActivity() {

    lateinit var lg_out:LinearLayout
    lateinit var back_btn:ImageView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        lg_out=findViewById(R.id.lg_out)
        back_btn=findViewById(R.id.back_btn)

        lg_out.setOnClickListener {
            var sp=getSharedPreferences("User_data", MODE_PRIVATE)
            val ed=sp.edit()
            ed.putString("user_mail","")
            ed.putString("user_pass","")
            ed.commit()
            Toast.makeText(this, "Logout successful", Toast.LENGTH_SHORT).show()
            startActivity(Intent(applicationContext,LoginActivity::class.java))
            finish()
        }

        back_btn.setOnClickListener {
            super.onBackPressed()
        }
    }
}