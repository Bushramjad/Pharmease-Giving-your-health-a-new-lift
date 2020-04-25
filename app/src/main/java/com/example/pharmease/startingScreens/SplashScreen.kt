package com.example.pharmease.startingScreens

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.pharmease.Drawer
import com.example.pharmease.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import kotlinx.android.synthetic.main.activity_splash_screen.signout


class SplashScreen : AppCompatActivity() {

    private val SPLASH_TIME_OUT:Long = 1000 // 1 sec
    private var firebaseAuth: FirebaseAuth? = null
    var mAuthListener: AuthStateListener? = null
    private var mAuth: FirebaseAuth? = null


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

//        Log.i("abc", FirebaseAuth.getInstance().currentUser.toString())
//
//        signout.setOnClickListener() {
//
//            FirebaseAuth.getInstance().signOut()
//            //mAuth?.signOut()
//            Log.i("def", FirebaseAuth.getInstance().currentUser.toString())
//        }
//
//
//        Log.i("abc", FirebaseAuth.getInstance().currentUser.toString())


        if( (FirebaseAuth.getInstance().getCurrentUser()) !=null)
        {
            Log.i("TAG", "success")
            startActivity(Intent(this, Drawer::class.java))
            finish()
        }
        else
        {
            Log.i("TAG", "failure")
            startActivity(Intent(this, Host::class.java))
            finish()
        }

//        Handler().postDelayed({
            // This method will be executed once the timer is over
            // Start your app main activity



//            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser()
//
//            if (user != null) {
//                // User is signed in
//
//            } else {
//                // User is signed out
//
//            }
//
//
//
//                    startActivity(Intent(this, Host::class.java))
            // close this activity
//            finish()
//        }, SPLASH_TIME_OUT)
    }
}

