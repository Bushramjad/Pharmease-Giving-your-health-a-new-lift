package com.example.pharmease.order

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pharmease.R
import com.example.pharmease.order.OrderFragment
import kotlinx.android.synthetic.main.order_history.*


class OrderFragment : Fragment() {

    private val medicines: ArrayList<String> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.order_history, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addMedicine()

        orderHistory.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        orderHistory.adapter = OrderAdaptar(medicines, requireActivity())
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
    }

}
