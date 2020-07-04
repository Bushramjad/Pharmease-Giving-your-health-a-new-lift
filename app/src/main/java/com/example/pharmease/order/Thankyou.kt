package com.example.pharmease.order

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pharmease.Drawer
import com.example.pharmease.R
import kotlinx.android.synthetic.main.thankyou.*

class Thankyou : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.thankyou)

        home.setOnClickListener {
            startActivity(Intent(this, Drawer::class.java))
        }


    }
}
