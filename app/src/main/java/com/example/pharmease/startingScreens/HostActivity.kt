package com.example.pharmease.startingScreens

import android.annotation.TargetApi
import android.app.Activity
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Build.VERSION_CODES.LOLLIPOP
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.pharmease.R


class Host : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.host)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.hide()

       // val firstscreen : CoordinatorLayout = findViewById(R.id.first_screen_layout)

        val navController = findNavController(R.id.nav_host_starting_screen)

        appBarConfiguration = AppBarConfiguration.Builder(navController.getGraph()).build()

        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_starting_screen)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}
