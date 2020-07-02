package com.example.pharmease.order

import java.util.*

data class OrderModel (
    val name: String = "",
    val status: String = "",
    val date: String = "",
    val price : String = "",
    var id : String = "") {
}