package com.example.rajadav.adidas_kotlin

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import com.example.rajadav.adidas_kotlin.model.Goal
import com.example.rajadav.adidas_kotlin.model.Items

class MainViewModel(private val context: Context) : ViewModel(){

    private val goalrepo: GoalsRepo

    init {
        goalrepo = GoalsRepo()
    }

    fun getGoals(): MutableLiveData<List<Goal>> {
        return goalrepo.getGoals()
    }
}