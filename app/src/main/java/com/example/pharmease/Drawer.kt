package com.example.pharmease

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.pharmease.startingScreens.Host
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.common.api.Status
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.drawer.*


class Drawer : AppCompatActivity() {


    private lateinit var appBarConfiguration: AppBarConfiguration

    lateinit var gso: GoogleSignInOptions

    private lateinit var mGoogleApiClient: GoogleSignInClient




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drawer)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


//        supportActionBar.setBackgroundDrawable(R.drawable.btn_gradient)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("478253187970-mba4a64o9b4ttlkv9j8rqa0m74eoi88e.apps.googleusercontent.com")
            .requestEmail()
            .build()
        mGoogleApiClient = GoogleSignIn.getClient(this, gso)



        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)

        val navView: NavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_profile, R.id.nav_pharmacies,R.id.nav_order,R.id.nav_signout,R.id.nav_settings, R.id.nav_feedback
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navView.menu.findItem(R.id.nav_signout)
            .setOnMenuItemClickListener { menuItem: MenuItem? ->
                logout()
                true
            }


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
//                Toast.makeText(applicationContext, "Cart in progress!", Toast.LENGTH_SHORT).show()
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


        val builder = AlertDialog.Builder(this)

            builder.setTitle("Signing out")
            builder.setMessage("Are you sure you want To Sign out?")

            builder.setPositiveButton("YES")
            { dialog, which ->
                Toast.makeText(applicationContext, "Signing out", Toast.LENGTH_SHORT).show()

                mGoogleApiClient.signOut()
                    .addOnCompleteListener(this, object : OnCompleteListener<Void?> {

                        override fun onComplete(p0: Task<Void?>) {

                            FirebaseAuth.getInstance().signOut()
                        }
                    })


                startActivity(Intent(this, Host::class.java))
            }

            builder.setNeutralButton("Cancel"){_,_ ->
//                Toast.makeText(applicationContext,"You cancelled the dialog.",Toast.LENGTH_SHORT).show()
            }

        val dialog: AlertDialog = builder.create()
            dialog.show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
        finish()
    }
}


