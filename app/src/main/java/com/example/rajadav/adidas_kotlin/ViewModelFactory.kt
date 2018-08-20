package com.example.rajadav.adidas_kotlin

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.Context

import com.example.rajadav.adidas_kotlin.data.GoalsRepo
import com.example.rajadav.adidas_kotlin.database.AppDatabase
import com.example.rajadav.adidas_kotlin.database.GoalDao
import com.example.rajadav.adidas_kotlin.data.Webservice

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/* Connect to retrofit and instanciate ViewModel */
class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(createGoalRepo(context)) as T
    }

    private fun createGoalRepo(context: Context): GoalsRepo {
        return GoalsRepo(createWebService(), createGoalDao(context), createExecutor())
    }

    fun createWebService(): Webservice {
        val gson = GsonBuilder().create()
        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        return retrofit.create(Webservice::class.java)
    }

    fun createExecutor(): Executor {
        return Executors.newSingleThreadExecutor()
    }

    fun createGoalDao(context: Context): GoalDao {
        val d: AppDatabase = AppDatabase.getInstance(context)
        return d.goalDao()
    }

    companion object {
        const val BASE_URL = "https://thebigachallenge.appspot.com/_ah/api/myApi/v1/"
    }

}