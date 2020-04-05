package com.example.pharmease.cart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pharmease.R
import kotlinx.android.synthetic.main.cart.*


class CartActivity : AppCompatActivity() {

    private val medicines: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cart)

        addMedicine()

        shopping_cart_recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        shopping_cart_recyclerView.adapter =
            CartAdaptar(this, medicines)

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
        Log.e("array", medicines.toString())
    }

}

