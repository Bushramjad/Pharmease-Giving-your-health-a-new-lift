package com.example.pharmease.startingScreens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pharmease.R

import kotlinx.android.synthetic.main.firstscreen.*

class FirstScreenActivity : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.firstscreen, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        next.setOnClickListener() {
            findNavController().navigate(R.id.action_nav_firstscreen_to_nav_login)
        }

        signup.setOnClickListener() {
            findNavController().navigate(R.id.action_nav_firstscreen_to_nav_signup1)
        }

    }
}