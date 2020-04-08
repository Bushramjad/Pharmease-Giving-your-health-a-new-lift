package com.example.pharmease.startingScreens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.pharmease.R
import kotlinx.android.synthetic.main.enable_location.*

/**
 * A simple [Fragment] subclass.
 */
class locationFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.enable_location, container, false)
        return root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        enable.setOnClickListener() {
            findNavController().navigate(R.id.action_nav_enable_location_notification_to_nav_congratulation)
        }
    }

}