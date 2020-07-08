package com.example.pharmease.startingScreens

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.pharmease.Drawer
import com.example.pharmease.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import kotlinx.android.synthetic.main.activity_splash_screen.signout


class SplashScreen : AppCompatActivity() {

    private var firebaseAuth: FirebaseAuth? = null
    var mAuthListener: AuthStateListener? = null
    private var mAuth: FirebaseAuth? = null

    private val SPLASH_TIME_OUT: Long = 3000 // 3 sec


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

//        startActivity(Intent(this, Host::class.java))

        Handler().postDelayed({

            if ((FirebaseAuth.getInstance().currentUser) != null) {
                Log.i("TAG", "success")
                startActivity(Intent(this, Drawer::class.java))
                finish()
            } else {
                Log.i("TAG", "failure")
                startActivity(Intent(this, Host::class.java))
                finish()
            }
            finish()
        }, SPLASH_TIME_OUT)

    }
}

