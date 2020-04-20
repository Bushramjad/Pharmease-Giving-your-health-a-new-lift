package com.example.pharmease.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.example.pharmease.R
import com.example.pharmease.api.EndPoints
import com.example.pharmease.api.VolleySingleton
import kotlinx.android.synthetic.main.signup_2.*
import org.json.JSONException
import org.json.JSONObject

class SignupSecond : Fragment() {

    private val SPLASH_TIME_OUT:Long = 1000 // 1 sec


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        val root = inflater.inflate(R.layout.signup_2, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBar4.visibility = View.GONE

        val email = arguments?.getString("email")
        val password = arguments?.getString("password")

        signup2.setOnClickListener() {

            progressBar4.visibility = View.VISIBLE
//            Handler().postDelayed({
//                findNavController().navigate(R.id.action_nav_signup2_to_nav_enable_location_notification)
//            }, SPLASH_TIME_OUT)

            addUser()
            findNavController().navigate(R.id.action_nav_signup2_to_nav_enable_location_notification)
        }
    }

    private fun addUser() {

        val fname = fname.text.toString().trim()
        val lname = lname.text.toString().trim()
        val phone = phone.text.toString().trim()

        //creating volley string request
        val stringRequest = object : StringRequest(Request.Method.POST, EndPoints.CREATE_USER,
            Response.Listener<String> { response ->
                try {
                    val obj = JSONObject(response)
                    Toast.makeText(activity, obj.getString("message"), Toast.LENGTH_LONG).show()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            object : Response.ErrorListener {
                override fun onErrorResponse(volleyError: VolleyError) {
                    Toast.makeText(activity, volleyError.message, Toast.LENGTH_LONG).show()
                }
            })
        {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params.put("email", fname)
                params.put("password", lname)
                params.put("name", phone)
                params.put("school", "0004")

                return params
            }
        }
        //adding request to queue
        VolleySingleton.instance?.addToRequestQueue(stringRequest)
    }
}