package com.example.home_automation

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import com.example.home_automation.Adpter.cus_adpter
import com.example.home_automation.Models.User_model
import com.example.home_automation.Models.itemModel
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class AddDevice : AppCompatActivity(),OnItemSelectedListener {

    lateinit var dev_name: EditText
    lateinit var dev_spn: Spinner
    lateinit var plc_spn: Spinner
    lateinit var add_btn: Button

    var dev_title = arrayOf("Select>","fan", "light")
    var dev_img = intArrayOf(0,R.drawable.fan, R.drawable.light)
    var plc_title = arrayOf(
        "Select>",
        "Bedroom",
        "Kitchen",
        "Livingroom",
        "Bathroom",
        "Balcony",
        "Parking",
        "Studyroom",
        "Office"
    )
    var plc_img = intArrayOf(
        0,
        R.drawable.bedroom,
        R.drawable.kitchen,
        R.drawable.living,
        R.drawable.bathroom,
        R.drawable.balcony,
        R.drawable.car,
        R.drawable.studyroom,
        R.drawable.office
    )

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_device)

        dev_name = findViewById(R.id.dev_name)
        dev_spn = findViewById(R.id.dev_spn)
        plc_spn = findViewById(R.id.plc_spn)
        add_btn=findViewById(R.id.add_btn)

        dev_spn.adapter=cus_adpter(applicationContext,dev_title,dev_img)
        dev_spn.onItemSelectedListener=this
        plc_spn.adapter=cus_adpter(applicationContext,plc_title,plc_img)
        plc_spn.onItemSelectedListener=this

        add_btn.setOnClickListener {
            var dev_pos=dev_spn.selectedItemPosition
            var plc_pos=dev_spn.selectedItemPosition

            if (dev_name.text.isEmpty()){
                Toast.makeText(applicationContext, "Please enter device name", Toast.LENGTH_SHORT).show()
            }else if (dev_pos==0){
                Toast.makeText(applicationContext, "Please Select device", Toast.LENGTH_SHORT).show()
            }else if (plc_pos==0){
                Toast.makeText(applicationContext, "Please Select place", Toast.LENGTH_SHORT).show()
            }else{
                val db = Firebase.database
                val ref = db.getReference("Device_data")
                ref.push().setValue(
                    itemModel(dev_name.text.toString().trim(),dev_title.get(dev_pos),plc_title.get(plc_pos),false)
                ).addOnCompleteListener {
                    Toast.makeText(this, "Device added successfull", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(applicationContext,MainActivity::class.java))
                }
            }
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }
}