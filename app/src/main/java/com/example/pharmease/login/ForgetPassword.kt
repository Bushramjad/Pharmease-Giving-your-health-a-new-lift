package com.example.pharmease.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.pharmease.R
import kotlinx.android.synthetic.main.forget_password.*


class ForgetPassword : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.forget_password, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        reset.setOnClickListener() {
            // findNavController().navigate(R.id.action_nav_firstscreen_to_nav_login)
            Toast.makeText(activity,"Pressed",Toast.LENGTH_SHORT).show()
        }
    }
}
