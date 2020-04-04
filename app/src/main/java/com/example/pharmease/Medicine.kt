package com.example.pharmease

import com.google.gson.annotations.SerializedName

data class Medicine(
    @SerializedName("description")
    var description: String? = null,

    @SerializedName("id")
    var id: Int? = null,

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("price")
    var price: String? = null

//    @SerializedName("photos")
//    var photos: List<Photo> = arrayListOf()
)