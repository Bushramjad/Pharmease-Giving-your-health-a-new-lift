package com.example.pharmease.cart

import com.example.pharmease.pharmacy.MedicineDataClass

data class Cartmodel(
    var medicines: MedicineDataClass,
    var quantity: Int = 0 )

