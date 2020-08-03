package com.example.pharmease.order

import java.util.*

data class OrderModel (
    val name: String = "",
    val status: String = "",
    val date: String = "",
    val amount : String = "",
    var phone : String = "",
    var address : String = "",
    var lat : Double,
    var lng : Double) {
}