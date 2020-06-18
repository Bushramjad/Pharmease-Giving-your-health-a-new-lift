package com.example.pharmease

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class Drawer : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drawer)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)

        val navView: NavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_profile, R.id.nav_pharmacies,R.id.nav_order,R.id.nav_signout,R.id.nav_help,R.id.nav_settings
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.drawer, menu)
//        menuInflater.inflate(R.menu.activity_drawer2_drawer, menu)

        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
   //     val navController = findNavController(R.id.nav_host_fragment)
        when (item.itemId) {
            R.id.action_settings -> {
//                Intent i = new Intent(this,CartActivity.class);
//                this.startActivity(i);
                return true;
            }
            R.id.action_cart -> {
                Toast.makeText(applicationContext, "Cart in progress!", Toast.LENGTH_SHORT).show()
//                val i = Intent(this, CartActivity::class.java)
//                startActivity(i)

                this.findNavController(R.id.nav_host_fragment).navigate(R.id.action_nav_home_to_nav_cart2)
                return true;
            }
            R.id.nav_signout -> {
                Toast.makeText(applicationContext,"Ok, we change the app background.",Toast.LENGTH_SHORT).show()
                logout()
                return true;
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    private fun logout() {

        Toast.makeText(applicationContext,"Ok, we change the app background.",Toast.LENGTH_SHORT).show()

        val builder = AlertDialog.Builder(this)

            builder.setTitle("Signing out")
            builder.setMessage("Are you sure you want ro logout?")

            builder.setPositiveButton("YES")
            {
                    dialog, which ->
                Toast.makeText(applicationContext,"Ok, we change the app background.",Toast.LENGTH_SHORT).show()
            }

            builder.setNeutralButton("Cancel"){_,_ ->
                Toast.makeText(applicationContext,"You cancelled the dialog.",Toast.LENGTH_SHORT).show()
            }

        val dialog: AlertDialog = builder.create()
            dialog.show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}


