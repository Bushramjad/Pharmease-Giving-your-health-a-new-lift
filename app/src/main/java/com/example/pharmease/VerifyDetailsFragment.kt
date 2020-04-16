package com.example.pharmease

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.verify_details.*


class VerifyDetailsFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.verify_details, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        confirm.setOnClickListener() {
            // findNavController().navigate(R.id.action_nav_firstscreen_to_nav_login)
           // Toast.makeText(activity,"Pressed", Toast.LENGTH_SHORT).show()
            startActivity(Intent(activity, Thankyou::class.java))
        }

        upload.setOnClickListener {
            //
        }
    }
    override fun onPrepareOptionsMenu(menu: Menu) {
        //super.onPrepareOptionsMenu(menu)
        menu.clear();
    }
}
