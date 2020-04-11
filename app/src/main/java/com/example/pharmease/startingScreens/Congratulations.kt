package com.example.pharmease.startingScreens

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.pharmease.Drawer
import com.example.pharmease.R
import kotlinx.android.synthetic.main.congratulation.*

class Congratulations : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        val root = inflater.inflate(R.layout.congratulation, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar2.visibility = View.GONE

        next.setOnClickListener() {
            progressBar2.visibility = View.VISIBLE

            startActivity(Intent(activity, Drawer::class.java))

        }
    }
}