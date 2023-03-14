package com.example.home_automation

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.example.home_automation.Models.User_model
import com.example.home_automation.Models.itemModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.regex.Matcher
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {

    lateinit var signup: TextView
    lateinit var mail: EditText
    lateinit var pass: EditText
    lateinit var omail: TextInputLayout
    lateinit var opass: TextInputLayout
    lateinit var login_btn: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        signup = findViewById(R.id.signup);
        login_btn = findViewById(R.id.login_btn)
        mail = findViewById(R.id.lg_mail)
        pass = findViewById(R.id.lg_pass)
        omail = findViewById(R.id.lgomail)
        opass = findViewById(R.id.lgopass)

        val sp = getSharedPreferences("User_data", MODE_PRIVATE)
        if (!sp.getString("user_mail", null).equals("") || !sp.getString("user_pass", null)
                .equals("")
        ) {
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        }

        mail.doOnTextChanged { text, start, before, count ->
            omail.error = null
        }
        pass.doOnTextChanged { text, start, before, count ->
            opass.error = null
        }

        login_btn.setOnClickListener {
            val db = Firebase.database
            val ref = db.getReference("Login_data")

            if (mail.text!!.isEmpty() && pass.text!!.isEmpty()) {
                omail.error = "Please enter your email id"
                opass.error = "Please enter your password"
            } else if (mail.text!!.isEmpty()) {
                omail.error = "Please enter your email id"
            } else if (!valid_mail(mail.text.toString())) {
                omail.error = "Please enter valid email"
            } else if (pass.text!!.isEmpty()) {
                opass.error = "Please enter your password"
            } else {
                var temp_res=true
                ref.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        for (ss in snapshot.children) {
                            var temp_list = ss.getValue(User_model::class.java)
                            if (temp_list!!.email!! == mail.text.toString() && temp_list.pass!! == pass.text.toString()) {
                                var sp = getSharedPreferences("User_data", MODE_PRIVATE)
                                val ed = sp.edit()
                                ed.putString("user_mail", mail.text.toString())
                                ed.putString("user_pass", pass.text.toString())
                                ed.commit()
                                startActivity(Intent(applicationContext, MainActivity::class.java))
                                Toast.makeText(applicationContext, "Login Successful", Toast.LENGTH_SHORT).show()
                                finish()
                            }
                            else{
                                temp_res=false
                            }
                        }
                        if (!temp_res){
                            Toast.makeText(applicationContext, "Email id or password is wrong", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })
            }
        }

        signup.setOnClickListener {
            startActivity(Intent(applicationContext, SignupActivity::class.java))
        }
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