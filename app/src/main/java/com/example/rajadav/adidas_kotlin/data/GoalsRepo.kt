package com.example.rajadav.adidas_kotlin.data

import android.arch.lifecycle.LiveData
import com.example.rajadav.adidas_kotlin.database.GoalDao
import com.example.rajadav.adidas_kotlin.model.*

import java.io.IOException
import java.util.concurrent.Executor

/*This class is connected with Room to manage the data*/
class GoalsRepo(val webservice: Webservice, val goalDao: GoalDao, val executor: Executor) {

    /*Return all the goals*/
    fun getGoals(): LiveData<List<Goal>> {
        var data = goalDao.loadAllGoals()
        executor.execute {
            try {
                val response = webservice.getgoals().execute()
                if (response.isSuccessful && response.body() != null) {
                    goalDao.insertGoals(response.body()!!.goals)
                } else {
                    //TODO return error to the upper layer somehow
                }
            } catch (e: IOException) {
                //TODO return error to the upper layer somehow
            }
        }
        return data
    }

    /*Return one goal by ID*/
    fun getGoal(id: Int): LiveData<Goal> {
        return goalDao.getGoalById(id)
    }

    /*Update one goal*/
    fun updateGoal(goal: Goal) {
        executor.execute {
            try {
                goalDao.updateGoal(goal)
            } catch (e: IOException) {
                //TODO return error to the upper layer somehow
            }
        }
    }

    /*Insert a Goal if it's done*/
    fun insertGoalsDone(goal: CompletedGoal) {
        executor.execute {
            goalDao.insertGoalCompletedIfNotExist(goal)
        }
    }

    /*Return all the goals done*/
    fun getGoalDone(): LiveData<List<CompletedGoal>> {
        return goalDao.loadAllGoalsDone()
    }

    /*Return the points earned today*/
    fun getTodayPoints(day: Int, month: Int, year: Int): LiveData<Int> {
        return goalDao.getTodayPoints(day, month, year)
    }

    /*Return all the points*/
    fun getAllPoints(): LiveData<Int> {
        return goalDao.getAllPoints()
    }

}
