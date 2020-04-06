package com.example.pharmease.login

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pharmease.R
import kotlinx.android.synthetic.main.forget_password.*

class ForgetPassword : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.forget_password)
        next.setOnClickListener() {
            Toast.makeText(applicationContext, "Reset screen in progress!", Toast.LENGTH_SHORT).show()

        }
    }
}