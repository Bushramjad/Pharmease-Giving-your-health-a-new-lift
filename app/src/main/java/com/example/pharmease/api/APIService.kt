package com.example.pharmease.api

import com.example.pharmease.models.DefaultResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface APIService {

    @FormUrlEncoded
    @POST("createuser")
    fun createUser(
        @Field("fname") fname: String?,
        @Field("lname") lname: String?,
        @Field("phone") phone: String?,
        @Field("abc") abc: String?
    ): Call<DefaultResponse?>?
}