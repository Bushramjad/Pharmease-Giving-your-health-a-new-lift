package com.example.pharmease.order

import com.example.pharmease.pharmacy.MedicineDataClass

data class testorder(
    val id : String = "",
    var medicines : MedicineDataClass)
{}