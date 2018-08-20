package com.example.rajadav.adidas_kotlin

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.example.rajadav.adidas_kotlin.data.GoalsRepo
import com.example.rajadav.adidas_kotlin.model.CompletedGoal
import com.example.rajadav.adidas_kotlin.model.Goal

/* ViewModel to manage the list of goals, getting one goal by id, and others methods */
class MainViewModel(val goalsRepo: GoalsRepo) : ViewModel() {

    fun getGoals(): LiveData<List<Goal>> {
        return goalsRepo.getGoals()
    }

    fun getGoal(id: Int): LiveData<Goal> {
        return goalsRepo.getGoal(id)
    }

    fun updateGoal(goal: Goal) {
        return goalsRepo.updateGoal(goal)
    }

    fun insertGoalDone(goal: CompletedGoal) {
        goalsRepo.insertGoalsDone(goal)
    }

    fun getGoalDone(): LiveData<List<CompletedGoal>> {
        return goalsRepo.getGoalDone()
    }

    fun getTodayPoints(day: Int, month: Int, year: Int): LiveData<Int> {
        return goalsRepo.getTodayPoints(day, month, year)
    }

    fun getTotalPoints(): LiveData<Int> {
        return goalsRepo.getAllPoints()
    }
}