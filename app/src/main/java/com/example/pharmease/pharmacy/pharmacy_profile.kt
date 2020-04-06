package com.example.pharmease.pharmacy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.pharmease.R
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pharmease.*
import kotlinx.android.synthetic.main.pharmacy_profile.*

/**
 * A simple [Fragment] subclass.
 */
class pharmacy_profile : Fragment() {


        private val medicines: ArrayList<String> = ArrayList()

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
        ): View? {

            val root = inflater.inflate(R.layout.pharmacy_profile, container, false)
            return root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            addMedicine()

            medicine_list.layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)
            medicine_list.adapter = MedicineAdapter(medicines, requireActivity())
            medicine_list.addOnItemClickListener(object: OnItemClickListener {
                override fun onItemClicked(position: Int, view: View) {
                     Toast.makeText(activity, "Good to go!", Toast.LENGTH_SHORT).show()
                    //startActivity(Intent(activity, CartActivity::class.java))

                }
            })
        }

        fun addMedicine() {
            medicines.add("Acetaminophen.")
            medicines.add("Adderall.")
            medicines.add("Amoxicillin.")
            medicines.add("Ativan.")
            medicines.add("Acetaminophen.")
            medicines.add("Adderall.")
            medicines.add("Ativan.")
            medicines.add("Acetaminophen")
            medicines.add("Adderall.")
            medicines.add("Ativan.")
            medicines.add("Amoxicillin.")
            medicines.add("Acetaminophen.")
            medicines.add("Adderall.")
            medicines.add("Amoxicillin.")
            medicines.add("Ativan.")
            medicines.add("Acetaminophen.")
            medicines.add("Adderall.")
            medicines.add("Ativan.")
            medicines.add("doAcetaminophen.g7")
            medicines.add("Adderall.")
            medicines.add("Ativan.")
            medicines.add("Amoxicillin.")
        }

    }

