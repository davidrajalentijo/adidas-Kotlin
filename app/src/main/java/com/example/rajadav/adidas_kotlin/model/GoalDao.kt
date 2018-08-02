package com.example.rajadav.adidas_kotlin.model

import retrofit2.Call
import retrofit2.http.GET

interface  GoalDao{

    @GET("./")
    fun getgoals(): Call<List<Goal>>
}