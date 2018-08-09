package com.example.rajadav.adidas_kotlin

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.example.rajadav.adidas_kotlin.data.GoalsRepo
import com.example.rajadav.adidas_kotlin.model.Goal

class MainViewModel(val goalsRepo: GoalsRepo) : ViewModel(){

    fun getGoals(): LiveData<List<Goal>> {
        return goalsRepo.getGoals()
    }

    fun getGoal(id : Int): LiveData<Goal>{
        return goalsRepo.getGoal(id)
    }

    fun updateGoal(goal: Goal){
        return goalsRepo.updateGoal(goal)
    }
}