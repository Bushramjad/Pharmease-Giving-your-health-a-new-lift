package com.example.pharmease.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.pharmease.Drawer
import com.example.pharmease.EndPoints
import com.example.pharmease.R
import com.example.pharmease.VolleySingleton

import kotlinx.android.synthetic.main.activity_login2.*
import org.json.JSONException
import org.json.JSONObject


class Login : AppCompatActivity() {

    private var username: EditText? = null
    private var password: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)

        username = findViewById(R.id.username) as EditText
        password = findViewById(R.id.password) as EditText

        forget.setOnClickListener() {
            startActivity(Intent(this, ForgetPassword::class.java))

        }

    }

    fun click(view:View)
    {
       // addArtist()
        startActivity(Intent(this, Drawer::class.java))

    }

    private fun addArtist() {
        //getting the record values
        val name = username?.text.toString()
        val pword = password?.text.toString()

        //creating volley string request
        val stringRequest = object : StringRequest(Request.Method.POST,
            EndPoints.URL_ADD_ARTIST,
            Response.Listener<String> { response ->
                Log.d("xyz",name)
                try {
                    val obj = JSONObject(response)
                    Toast.makeText(applicationContext, obj.getString("message"), Toast.LENGTH_LONG)
                        .show()
                    Log.d("abc",name)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { volleyError ->
                Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG)
                    .show()
            })

        {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["name"] = name
                params["genre"] = pword
                return params
            }
        } //adding request to queue
        VolleySingleton.instance?.addToRequestQueue(stringRequest)


    }
}