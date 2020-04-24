package com.example.pharmease.login

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pharmease.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.forget_password.*
import kotlinx.android.synthetic.main.forget_password.email


class ForgetPassword : Fragment() {

    private var mAuth: FirebaseAuth? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.forget_password, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAuth = FirebaseAuth.getInstance()

        reset.setOnClickListener() {
            // findNavController().navigate(R.id.action_nav_firstscreen_to_nav_login)
//            Toast.makeText(activity,"Pressed",Toast.LENGTH_SHORT).show()
            sendPasswordResetEmail()
        }
    }

    private fun sendPasswordResetEmail() {

        val email = email.text.toString().trim()

        if (!TextUtils.isEmpty(email)) {
            mAuth!!
                .sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        val message = "Email sent."
                        Log.d("TAG", message)
                        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
                        updateUI()
                    } else {
                        Log.w("TAG", task.exception!!.message)
                        Toast.makeText(requireActivity(), "No user found with this email.", Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(requireActivity(), "Enter Email", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI() {
//        findNavController().navigate(R.id.action_nav_firstscreen_to_nav_login)
    }
}
