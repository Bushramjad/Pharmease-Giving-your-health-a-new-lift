package com.example.pharmease.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pharmease.R
import com.example.pharmease.api.RetrofitClient
import com.example.pharmease.models.DefaultResponse
import kotlinx.android.synthetic.main.signup_2.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        signup2.setOnClickListener() {

            progressBar4.visibility = View.VISIBLE
//            Handler().postDelayed({
//                findNavController().navigate(R.id.action_nav_signup2_to_nav_enable_location_notification)
//            }, SPLASH_TIME_OUT)


            val fname = fname.text.toString().trim()
            val lname = lname.text.toString().trim()
            val phone = phone.text.toString().trim()

//            if(fname.isEmpty()){
//                fname.error = "First name Required"
//                fname.requestFocus()
//                return@setOnClickListener
//            }

            RetrofitClient.instance.createUser("email", "name", "password", "school")
                ?.enqueue(object: Callback<DefaultResponse?>{

                    override fun onFailure(call: Call<DefaultResponse?>, t: Throwable) {
                        Toast.makeText(activity,t.message,Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<DefaultResponse?>, response: Response<DefaultResponse?>) {
                        Toast.makeText(activity,response.body()?.message,Toast.LENGTH_SHORT).show()
                    }

                })

            //findNavController().navigate(R.id.action_nav_signup2_to_nav_enable_location_notification)

        }
    }
}