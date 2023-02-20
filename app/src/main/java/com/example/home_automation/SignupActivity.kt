package com.example.home_automation

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class SignupActivity : AppCompatActivity() {

    lateinit var login: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        login=findViewById(R.id.login);

        login.setOnClickListener {
            startActivity(Intent(applicationContext,LoginActivity::class.java))
        }
    }
}