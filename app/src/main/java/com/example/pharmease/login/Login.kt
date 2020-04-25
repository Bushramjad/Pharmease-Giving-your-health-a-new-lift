package com.example.pharmease.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pharmease.Drawer
import com.example.pharmease.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.login.*


class Login : Fragment() {

    private var mAuth: FirebaseAuth? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.login, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBar3.visibility = View.GONE
        mAuth = FirebaseAuth.getInstance()

        loginButton.setOnClickListener() {
            loginUser()
        }

        forget.setOnClickListener() {
            findNavController().navigate(R.id.action_nav_login_to_nav_forget_password)
        }
    }

    private fun loginUser() {

        val email = email.text.toString().trim()
        val password = password.text.toString().trim()

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {

//            mProgressBar!!.setMessage("Registering User...")
//            mProgressBar!!.show()
            progressBar3.visibility = View.VISIBLE


//            Log.d(activity, "Logging in user.")

            mAuth!!.signInWithEmailAndPassword(email!!, password!!)
                .addOnCompleteListener(requireActivity()) { task ->

//                    mProgressBar!!.hide()

                    if (task.isSuccessful) {
                        // Sign in success, update UI with signed-in user's information
                        Log.d("activity", "signInWithEmail:success")
                        updateUI()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.e("activity", "signInWithEmail:failure", task.exception)
                        Toast.makeText(requireActivity(), "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(activity, "Enter all details", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI() {
        startActivity(Intent(activity, Drawer::class.java))
        activity?.finish()
    }
}