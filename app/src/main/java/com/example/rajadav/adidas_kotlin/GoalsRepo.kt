package com.example.rajadav.adidas_kotlin

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.util.Log
import com.example.rajadav.adidas_kotlin.model.Goal
import com.example.rajadav.adidas_kotlin.model.Items

import com.example.rajadav.adidas_kotlin.model.Webservice
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GoalsRepo(){

    val BASE_URL="https://thebigachallenge.appspot.com/_ah/api/myApi/v1/"
    var webservice:Webservice?=null

    init{
        val gson = GsonBuilder().create();
        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        webservice = retrofit.create(Webservice::class.java)
    }

    /*
    companion object {
        private var goalsRepository: GoalsRepo? = null
        @Synchronized
        @JvmStatic
        fun getInstance(): GoalsRepo {
            if (goalsRepository == null) {
                goalsRepository = GoalsRepo()
            }
            return goalsRepository!!
        }
    }
*/

    fun getGoals(): MutableLiveData<List<Goal>> {
        val data = MutableLiveData<List<Goal>>()
        webservice?.getgoals()?.enqueue(object : Callback<Items> {
            override fun onResponse(call: Call<Items>, response: Response<Items>?) {
                data.value=response!!.body()!!.goals
            }

            override fun onFailure(call: Call<Items>, t: Throwable) {
                Log.d("onFailure", "data")
                // TODO better error handling in part #2 ...
                data.value=null
            }
        })
        return data
    }

}