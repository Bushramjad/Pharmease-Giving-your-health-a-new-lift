package com.example.pharmease.signup

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.pharmease.R
import kotlinx.android.synthetic.main.signup_2.*

/**
 * A simple [Fragment] subclass.
 */
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

                Handler().postDelayed({
                    findNavController().navigate(R.id.action_nav_signup2_to_nav_enable_location_notification)
                }, SPLASH_TIME_OUT)



        }
    }
}