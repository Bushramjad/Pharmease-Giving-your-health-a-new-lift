package com.example.pharmease.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pharmease.R
import kotlinx.android.synthetic.main.cart.*

class CartFragment : Fragment() {

    private val medicines: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.cart, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addMedicine()

        checkout.setOnClickListener() {
            findNavController().navigate(R.id.action_nav_cart_to_nav_verify_details)
        }

        shopping_cart_recyclerView.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        shopping_cart_recyclerView.adapter = CartAdaptar(requireActivity(), medicines)
    }

    fun addMedicine() {
        medicines.add("Acetaminophen.")
        medicines.add("Adderall.")
        medicines.add("Amoxicillin.")
        medicines.add("Ativan.")
        medicines.add("Acetaminophen.")
        medicines.add("Adderall.")
        medicines.add("Adderall.")
        medicines.add("Amoxicillin.")
        medicines.add("Ativan.")
        medicines.add("Acetaminophen.")
        medicines.add("Adderall.")
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        //super.onPrepareOptionsMenu(menu)
        menu.clear();
    }
}
