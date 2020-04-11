package com.example.pharmease.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pharmease.Drawer
import com.example.pharmease.R
import kotlinx.android.synthetic.main.activity_login2.*
import kotlinx.android.synthetic.main.congratulation.*


class Login : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.activity_login2, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBar3.visibility = View.GONE

        loginButton.setOnClickListener() {

            progressBar3.visibility = View.VISIBLE

            startActivity(Intent(activity, Drawer::class.java))
        }

        forget.setOnClickListener() {
            findNavController().navigate(R.id.action_nav_login_to_nav_forget_password)
        }

    }
}