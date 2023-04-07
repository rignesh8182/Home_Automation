package com.example.home_automation

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.home_automation.Models.User_model
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Profile_Activity : AppCompatActivity() {

    lateinit var lg_out:LinearLayout
    lateinit var back_btn:ImageView
    lateinit var name:TextView
    lateinit var number:TextView
    lateinit var user_mail:String
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        lg_out=findViewById(R.id.lg_out)
        back_btn=findViewById(R.id.back_btn)
        name=findViewById(R.id.user_name)
        number=findViewById(R.id.user_number)

        var sp = getSharedPreferences("User_data", AppCompatActivity.MODE_PRIVATE)
        user_mail= sp!!.getString("user_mail",null).toString()

        val db= FirebaseDatabase.getInstance().getReference("Login_data")

        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (ss in snapshot.children){
                    var temp_list=ss.getValue(User_model::class.java)
                    if (temp_list!!.email.equals(user_mail)){
                        name.text=temp_list.name
                        number.text=temp_list.mob
                        break
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

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