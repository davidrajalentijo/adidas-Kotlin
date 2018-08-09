package com.example.rajadav.adidas_kotlin.data

import com.example.rajadav.adidas_kotlin.model.Items
import retrofit2.Call
import retrofit2.http.GET

interface Webservice{

    @GET("goals")
    fun getgoals(): Call<Items>
}