package com.example.pharmease

import android.app.Application
import com.example.pharmease.pharmacy.AllPharmaciesModel
import java.util.ArrayList

class MyApplication : Application(){

    lateinit var PharmaciesList: ArrayList<AllPharmaciesModel>

//    fun getPharmaciesList(): ArrayList<AllPharmaciesModel> {
//        return PharmaciesList
//    }
//
//    fun setPharmaciesList(someVariable: ArrayList<AllPharmaciesModel>) {
//        this.PharmaciesList = someVariable
//    }

}