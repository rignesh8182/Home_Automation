package com.example.home_automation

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.home_automation.Models.User_model
import com.example.home_automation.Models.itemModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    lateinit var signup:TextView
    lateinit var mail:EditText
    lateinit var pass:EditText
    lateinit var login_btn:Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        signup=findViewById(R.id.signup);
        login_btn=findViewById(R.id.login_btn)
        mail=findViewById(R.id.lg_mail)
        pass=findViewById(R.id.lg_pass)

        val sp=getSharedPreferences("User_data", MODE_PRIVATE)
        if (!sp.getString("user_mail",null).equals("") || !sp.getString("user_pass",null).equals("")){
            startActivity(Intent(applicationContext,MainActivity::class.java))
            finish()
        }

        login_btn.setOnClickListener {
            val db=Firebase.database
            val ref=db.getReference("Login_data")

            ref.addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (ss in snapshot.children) {
                        var temp_list=ss.getValue(User_model::class.java)
                        if (temp_list!!.email!! == mail.text.toString() && temp_list.pass!! == pass.text.toString()){
                            var sp=getSharedPreferences("User_data", MODE_PRIVATE)
                            val ed=sp.edit()
                            ed.putString("user_mail",mail.text.toString())
                            ed.putString("user_pass",pass.text.toString())
                            ed.commit()
                            startActivity(Intent(applicationContext,MainActivity::class.java))
                            finish()
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }

        signup.setOnClickListener {
            startActivity(Intent(applicationContext,SignupActivity::class.java))
        }
    }
}