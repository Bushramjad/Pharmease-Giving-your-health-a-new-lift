package com.example.pharmease.signup

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pharmease.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.signup_2.*


val TAG = "CreateAccountActivity"
class SignupSecond : Fragment() {

    private val SPLASH_TIME_OUT:Long = 1000 // 1 sec

    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        val root = inflater.inflate(R.layout.signup_2, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference.child("Users")
        mAuth = FirebaseAuth.getInstance()

        progressBar4.visibility = View.GONE

        signup2.setOnClickListener() {

            when {
                fname.text.toString().trim() == "" -> {
                    showSnackBar("Please enter First name")
                }
                lname.text.toString().trim() == "" -> {
                    showSnackBar("Please enter Last name")
                }
                phone.text.toString().trim() == "" -> {
                    showSnackBar("Please enter valid Phone number")
                }
                else -> createNewAccount()
            }
        }
    }


    private fun createNewAccount() {
        val email = arguments?.getString("email")
        val password = arguments?.getString("password")
        val fname = fname.text.toString().trim()
        val lname = lname.text.toString().trim()
        val phone = phone.text.toString().trim()

        progressBar4.visibility = View.VISIBLE

        mAuth!!
            .createUserWithEmailAndPassword(email!!, password!!)
            .addOnCompleteListener(requireActivity()) { task ->
                //mProgressBar!!.hide()

                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")

                    val userId = mAuth!!.currentUser!!.uid

                    //Verify Email
                    verifyEmail();

                    //update user profile information
                    val currentUserDb = mDatabaseReference!!.child(userId)
                    currentUserDb.child("fname").setValue(fname)
                    currentUserDb.child("lname").setValue(lname)
                    currentUserDb.child("phone").setValue(phone)

                    updateUserInfoAndUI()

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    showSnackBar("Email already exist, Try login to your account or create one with different Email address.")

                }
            }
    }


    private fun updateUserInfoAndUI() {
        //start next activity
        findNavController().navigate(R.id.action_nav_signup2_to_nav_congratulation)
    }

    private fun verifyEmail() {
        val mUser = mAuth!!.currentUser;
        mUser!!.sendEmailVerification()
            .addOnCompleteListener(requireActivity()) { task ->

                if (task.isSuccessful) {
                    showSnackBar( "Verification email sent to " + mUser.email)
                } else {
                    Log.e(TAG, "sendEmailVerification", task.exception)
                    showSnackBar("Failed to send verification email.")
                }
            }
    }

    private fun showSnackBar(msg :String) {
        val snackBar = Snackbar.make(requireActivity().findViewById(android.R.id.content),
            msg, Snackbar.LENGTH_LONG
        )
        snackBar.show()
    }
}