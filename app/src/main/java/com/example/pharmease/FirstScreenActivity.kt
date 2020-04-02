package com.example.pharmease

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.firstscreen.*

class FirstScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.firstscreen)

        next.setOnClickListener() {
            startActivity(Intent(this, Login::class.java))
        }

        signup.setOnClickListener() {
            startActivity(Intent(this,Congratulations::class.java))
            //Toast.makeText(applicationContext, "Signup screen in progress!", Toast.LENGTH_SHORT).show()
        }
    }

}
