package com.example.pharmease.startingScreens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pharmease.Drawer
import com.example.pharmease.R
import kotlinx.android.synthetic.main.congratulation.*

class Congratulations : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.congratulation)
        next.setOnClickListener() {
            startActivity(Intent(this, Drawer::class.java))

        }
    }
}
