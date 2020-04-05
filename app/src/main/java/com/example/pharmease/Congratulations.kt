package com.example.pharmease

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.congratulation.*

class Congratulations : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.congratulation)
        next.setOnClickListener() {
            startActivity(Intent(this,Drawer::class.java))

        }
    }
}
