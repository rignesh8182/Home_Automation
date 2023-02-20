package com.example.home_automation

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class LoginActivity : AppCompatActivity() {

    lateinit var signup:TextView
    lateinit var login_btn:Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        signup=findViewById(R.id.signup);
        login_btn=findViewById(R.id.login_btn)

        login_btn.setOnClickListener {
            startActivity(Intent(applicationContext,MainActivity::class.java))
            finish()
        }

        signup.setOnClickListener {
            startActivity(Intent(applicationContext,SignupActivity::class.java))
        }
    }
}