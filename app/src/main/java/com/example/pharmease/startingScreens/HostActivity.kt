package com.example.pharmease.startingScreens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
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
