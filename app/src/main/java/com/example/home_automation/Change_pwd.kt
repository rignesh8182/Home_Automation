package com.example.home_automation

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.home_automation.Models.User_model
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Change_pwd : AppCompatActivity() {

    lateinit var old_pass:EditText
    lateinit var new_pass:EditText
    lateinit var renew_pass:EditText
    lateinit var up_pass:Button
    lateinit var user_mail:String

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_pwd)

        old_pass=findViewById(R.id.old_pass)
        new_pass=findViewById(R.id.new_pass)
        renew_pass=findViewById(R.id.renew_pass)
        up_pass=findViewById(R.id.update_pass)

        up_pass.setOnClickListener {
            var sp = getSharedPreferences("User_data", AppCompatActivity.MODE_PRIVATE)
            user_mail= sp!!.getString("user_mail",null).toString()

            val db= FirebaseDatabase.getInstance().getReference("Login_data")

            db.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var key:String
                    for (ss in snapshot.children){
                        var temp_list=ss.getValue(User_model::class.java)
                        if (temp_list!!.email.equals(user_mail)){
                            key=ss.key.toString()
                            if (old_pass.text.toString().equals(temp_list.pass)){
                                if (new_pass.text.toString().equals(renew_pass.text.toString())){
                                    val db = Firebase.database
                                    val ref = db.getReference("Login_data")
                                    ref.child(key).child("pass").setValue(new_pass.text.toString()).addOnCompleteListener {
                                        startActivity(Intent(applicationContext,Profile_Activity::class.java))
                                        finish()
                                    }
                                }else{
                                    Toast.makeText(applicationContext, "New and re-type password is not matching", Toast.LENGTH_SHORT).show()
                                }
                            }else{
                                Toast.makeText(applicationContext, "Old password is incorrect", Toast.LENGTH_SHORT).show()
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

    }
}