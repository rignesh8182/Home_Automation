package com.example.home_automation

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.home_automation.Models.User_model
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Edit_profile : AppCompatActivity() {

    lateinit var em_nm:EditText
    lateinit var em_em:EditText
    lateinit var em_mob:EditText
    lateinit var cng_pass:TextView
    lateinit var update_btn:Button
    lateinit var user_mail:String
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        em_nm=findViewById(R.id.edt_nm)
        em_em=findViewById(R.id.edt_em)
        em_mob=findViewById(R.id.edt_mob)
        update_btn=findViewById(R.id.update)
        cng_pass=findViewById(R.id.cng_pass)

        var sp = getSharedPreferences("User_data", AppCompatActivity.MODE_PRIVATE)
        user_mail= sp!!.getString("user_mail",null).toString()

        val db= FirebaseDatabase.getInstance().getReference("Login_data")

        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (ss in snapshot.children){
                    var temp_list=ss.getValue(User_model::class.java)
                    if (temp_list!!.email.equals(user_mail)){
                        em_nm.setText(temp_list.name)
                        em_em.setText(temp_list.email)
                        em_mob.setText(temp_list.mob)
                        em_em.isEnabled=false
                        break
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        update_btn.setOnClickListener {
            val db= FirebaseDatabase.getInstance().getReference("Login_data")

            db.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var key:String
                    for (ss in snapshot.children){
                        var temp_list=ss.getValue(User_model::class.java)
                        if (temp_list!!.email.equals(user_mail)){
                            key= ss.key.toString()
                            val db = Firebase.database
                            val ref = db.getReference("Login_data")
                            ref.child(key).child("name").setValue(em_nm.text.toString()).addOnCompleteListener {
                                ref.child(key).child("mob").setValue(em_mob.text.toString()).addOnCompleteListener {
                                    startActivity(Intent(applicationContext,Profile_Activity::class.java))
                                    finish()
                                }
                            }
                            break
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }

        cng_pass.setOnClickListener {
            startActivity(Intent(applicationContext,Change_pwd::class.java))
            finish()
        }
    }
}