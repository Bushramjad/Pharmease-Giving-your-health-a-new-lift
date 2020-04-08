package com.example.pharmease.signup

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.pharmease.R
import kotlinx.android.synthetic.main.signup_2.*

/**
 * A simple [Fragment] subclass.
 */
class SignupSecond : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.signup_2, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            signup2.setOnClickListener() {
                Log.i("val", findNavController().currentDestination?.id.toString())

              //  if (it.findNavController().currentDestination?.id == R.id.nav_signup2) {
                    findNavController().navigate(R.id.action_nav_signup2_to_nav_enable_location_notification)
               // }

           // findNavController().navigate(R.id.action_nav_signup2_to_nav_enable_location)
        }
    }
}