package com.example.home_automation.Fragments

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
//import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.home_automation.R
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class HomeFragment : Fragment() {

    var temp: String = String()
    lateinit var bed:LinearLayout
    lateinit var home_temp:TextView
    lateinit var weth_con:TextView
    lateinit var loc:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootview = inflater.inflate(R.layout.fragment_home, container, false)

        bed=rootview.findViewById(R.id.hm_bed)
        home_temp=rootview.findViewById(R.id.home_tem)
        weth_con=rootview.findViewById(R.id.weth_condi)
        loc=rootview.findViewById(R.id.loc)

        bed.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.frame, BedroomFragment()).commit()
        }

        val SDK_INT = Build.VERSION.SDK_INT
        if (SDK_INT > 8) {
            val policy = StrictMode.ThreadPolicy.Builder()
                .permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }

        val progress=ProgressDialog(activity)
        progress.setCancelable(false)
        progress.setMessage("Fetching weather")
        progress.show()

        val request1 = JsonObjectRequest(com.android.volley.Request.Method.GET, "https://api.ipify.org/?format=json", null, {
                response ->
            temp = response["ip"].toString()


            val loc_request= JsonObjectRequest(com.android.volley.Request.Method.GET,"https://ipinfo.io/$temp/geo",null,{
                    response ->

                val latlng=response["loc"].toString()
                val client = OkHttpClient()
                val request = okhttp3.Request.Builder()
                    .url("https://weatherapi-com.p.rapidapi.com/current.json?q=" + latlng)
                    .get()
                    .addHeader("X-RapidAPI-Key", "01ab16f7ebmshf25abc4b350d911p1b05c5jsnbf41b2192261")
                    .addHeader("X-RapidAPI-Host", "weatherapi-com.p.rapidapi.com")
                    .build()
                val response = client.newCall(request).execute()
                val res: String = response.body?.string()!!
                val jsonObject: JSONObject = JSONObject(res)
                val mainObject: JSONObject = jsonObject.getJSONObject("current")
                val restemp:String = mainObject.getString("temp_c")
                val condition:JSONObject=mainObject.getJSONObject("condition")
                val location:JSONObject=jsonObject.getJSONObject("location")
                weth_con.text=condition.getString("text")
                home_temp.text= "$restemp°c"
                val loc_city=location.getString("name")
                val loc_state=location.getString("region")
                loc.text="$loc_city,$loc_state"
                if (progress.isShowing){
                    progress.dismiss()
                }
            },{})
            Volley.newRequestQueue(activity).add(loc_request)

        }, { })
        Volley.newRequestQueue(activity).add(request1)



        return rootview
    }
}