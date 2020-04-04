package com.example.pharmease

import android.content.Intent
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pharmease.ui.dashboard.MedicineAdapter
import kotlinx.android.synthetic.main.cart.*


class CartActivity : AppCompatActivity() {

    private val medicines: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cart)

        addMedicine()

        shopping_cart_recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        shopping_cart_recyclerView.adapter = CartAdaptar(this, medicines)

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

