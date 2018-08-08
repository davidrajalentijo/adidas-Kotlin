package com.example.rajadav.adidas_kotlin

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import com.example.rajadav.adidas_kotlin.model.Goal
import com.example.rajadav.adidas_kotlin.model.Items

class MainViewModel(val goalsRepo: GoalsRepo) : ViewModel(){

    fun getGoals(): LiveData<List<Goal>> {
        return goalsRepo.getGoals()
    }
}