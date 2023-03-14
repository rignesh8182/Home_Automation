package com.example.home_automation

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.example.home_automation.Models.User_model
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.regex.Matcher
import java.util.regex.Pattern

class SignupActivity : AppCompatActivity() {

    lateinit var login: TextView
    lateinit var name: EditText
    lateinit var email: EditText
    lateinit var pass: EditText
    lateinit var cpass: EditText
    lateinit var mob: EditText
    lateinit var signup: Button
    lateinit var onm: TextInputLayout
    lateinit var oem: TextInputLayout
    lateinit var opass: TextInputLayout
    lateinit var ocpass: TextInputLayout
    lateinit var omob: TextInputLayout

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        login = findViewById(R.id.login)
        name = findViewById(R.id.nm)
        email = findViewById(R.id.em)
        mob = findViewById(R.id.mob)
        pass = findViewById(R.id.pass)
        cpass = findViewById(R.id.cpass)
        signup = findViewById(R.id.signup)
        onm = findViewById(R.id.onm)
        oem = findViewById(R.id.oem)
        opass = findViewById(R.id.opass)
        ocpass = findViewById(R.id.ocpass)
        omob = findViewById(R.id.omob)

        login.setOnClickListener {
            startActivity(Intent(applicationContext, LoginActivity::class.java))
        }


        pass.doOnTextChanged { text, start, before, count ->
            opass.error = null
        }
        cpass.doOnTextChanged { text, start, before, count ->
            ocpass.error = null
        }
        mob.doOnTextChanged { text, start, before, count ->
            omob.error = null
        }

        signup.setOnClickListener {
            if (name.text.isEmpty() && email.text.isEmpty() && pass.text.isEmpty() && cpass.text.isEmpty() && mob.text.isEmpty()) {
                onm.error = "Name is required"
                oem.error = "Email is required"
                opass.error = "Password is required"
                ocpass.error = "Re-type the password"
                omob.error = "Mobile number is required"
            } else if (email.text.isEmpty()) {
                oem.error = "Email is required"
            } else if (!valid_mail(email.text.toString())) {
                oem.error = "Enter valid mail"
            } else if (name.text.isEmpty()) {
                onm.error = "Name is required"
            } else if (pass.text.isEmpty()) {
                opass.error = "Password is required"
            } else if (cpass.text.isEmpty()) {
                ocpass.error = "Re-type the password"
            }else if (pass.text.toString() != cpass.text.toString()){
                ocpass.error="Re-type password must be same as password"
            } else if (mob.text.isEmpty()) {
                omob.error = "Mobile number is required"
            } else if (valid_mob(mob.text.toString())) {
                omob.error = "Enter valid Mobile number"
            } else {
                val db = Firebase.database
                val ref = db.getReference("Login_data")
                ref.push().setValue(
                    User_model(
                        System.currentTimeMillis(),
                        name.text.toString(),
                        email.text.toString(),
                        pass.text.toString(),
                        mob.text.toString()
                    )
                ).addOnCompleteListener {
                    Toast.makeText(this, "Registration successfull", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(applicationContext,LoginActivity::class.java))
                }
            }
        }
    }

    private fun valid_mob(mob: String): Boolean {
        return mob.length < 10
    }

    private fun valid_mail(mail: String): Boolean {
        val pattern: Pattern
        val matcher: Matcher
        val EMAIL_PATTERN =
            "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
        pattern = Pattern.compile(EMAIL_PATTERN)
        matcher = pattern.matcher(mail)
        return matcher.matches()
    }
}