package com.example.pharmease

import android.content.Context
import android.content.Intent
import android.content.QuickViewConstants
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.example.pharmease.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login2.*


class Login : AppCompatActivity() {

    private val SPLASH_TIME_OUT:Long = 1000 // 1 sec


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)
       
    }



    fun click(view:View)
    {
        startActivity(Intent(this,MapsActivity::class.java))
    }



}