package com.example.pharmease.pharmacy

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pharmease.OnItemClickListener
import com.example.pharmease.R
import com.example.pharmease.addOnItemClickListener
import com.example.pharmease.pharmacy.AllPharmaciesAdaptor
import kotlinx.android.synthetic.main.all_pharmacies.*


class AllPharmacies : Fragment() {

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
        val root = inflater.inflate(R.layout.all_pharmacies, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addMedicine()

        recycler_view.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        recycler_view.adapter = AllPharmaciesAdaptor(medicines, requireActivity())

        recycler_view.addOnItemClickListener(object: OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                // Toast.makeText(activity, "List view in progress!", Toast.LENGTH_SHORT).show()
                //startActivity(Intent(activity, CartActivity::class.java))
                requireActivity().findNavController(R.id.nav_host_fragment).navigate(R.id.action_nav_pharmacies_to_nav_pharmacy_profile)

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
    override fun onPrepareOptionsMenu(menu: Menu) {
        //super.onPrepareOptionsMenu(menu)
        menu.clear();
    }

}
