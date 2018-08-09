package com.example.rajadav.adidas_kotlin.data

import android.arch.lifecycle.LiveData
import com.example.rajadav.adidas_kotlin.database.GoalDao
import com.example.rajadav.adidas_kotlin.model.*

import java.io.IOException
import java.util.concurrent.Executor

class GoalsRepo(val webservice: Webservice, val goalDao: GoalDao, val executor: Executor ){

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

    fun getGoal(id: Int): LiveData<Goal>{
        return goalDao.getGoalById(id)
    }

    fun updateGoal(goal: Goal){
        executor.execute {
            try {
                goalDao.updateGoal(goal)
            } catch (e: IOException) {
                //TODO return error to the upper layer somehow
            }
        }
    }

}
