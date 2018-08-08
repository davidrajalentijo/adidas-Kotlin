package com.example.rajadav.adidas_kotlin

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.util.Log
import com.example.rajadav.adidas_kotlin.model.*
import com.example.rajadav.adidas_kotlin.model.AppDatabase

import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class GoalsRepo(val webservice:Webservice, val goalDao: GoalDao, val executor: Executor ){

    fun getGoals(): LiveData<List<Goal>> {
        var data = goalDao.loadAllGoals()
        executor.execute {
            try {
                val response = webservice.getgoals().execute()
                if (response.isSuccessful && response.body() != null) {
                    goalDao.insertGoals(response.body()!!.goals)
                } else {
                    //TODO return error to the upper layer somehow
                    //response.error
                }

            } catch (e: IOException) {
                //TODO return error to the upper layer somehow
            }
        }

        return data
    }

}
