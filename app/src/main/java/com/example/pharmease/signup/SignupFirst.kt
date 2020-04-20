package com.example.pharmease.signup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.pharmease.R
import kotlinx.android.synthetic.main.signup_1.*

/**
 * A simple [Fragment] subclass.
 */
class SignupFirst : Fragment() {

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
        ): View? {
            (activity as AppCompatActivity?)!!.supportActionBar!!.show()

            val root = inflater.inflate(R.layout.signup_1, container, false)
            return root
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        next.setOnClickListener() {

            val email = email.text.toString().trim()
            val password = password.text.toString().trim()
            val cpassword = cpassword.text.toString().trim()


            if (it.findNavController().currentDestination?.id == R.id.nav_signup1) {

                val bundle =  bundleOf("email" to email, "password" to password)

                it.findNavController().navigate(R.id.action_nav_signup1_to_nav_signup2,bundle)
            }
        }

    }
}