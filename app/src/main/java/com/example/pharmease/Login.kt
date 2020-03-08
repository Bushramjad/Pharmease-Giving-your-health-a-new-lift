package com.example.pharmease

import android.content.Intent
import android.content.QuickViewConstants
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;


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