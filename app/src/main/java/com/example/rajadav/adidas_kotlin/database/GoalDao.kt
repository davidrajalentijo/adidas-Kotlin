package com.example.rajadav.adidas_kotlin.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.example.rajadav.adidas_kotlin.model.CompletedGoal
import com.example.rajadav.adidas_kotlin.model.Goal

@Dao
abstract class GoalDao {

    @Query("SELECT * FROM goal")
    abstract fun loadAllGoals(): LiveData<List<Goal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertGoals(goal: List<Goal>)

    @Query("SELECT * FROM goal WHERE id =:id")
    abstract fun getGoalById(id: Int): LiveData<Goal>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract fun updateGoal(goal: Goal)

    @Query("SELECT * FROM goalsdone ORDER BY year DESC, month DESC, day DESC, hour DESC, minutes DESC, seconds DESC")
    abstract fun loadAllGoalsDone(): LiveData<List<CompletedGoal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertGoalsDone(goal: CompletedGoal)

    @Query("SELECT * FROM goalsdone WHERE day=:day AND month=:month AND year=:year AND goalid=:goalid LIMIT 1")
    abstract fun goalIsDone(day: Int, month: Int, year: Int, goalid: Int): CompletedGoal

    @Query("SELECT SUM(points) FROM goalsdone")
    abstract fun getAllPoints(): LiveData<Int>

    @Query("SELECT SUM(points) FROM goalsdone WHERE day=:day AND month=:month AND year=:year")
    abstract fun getTodayPoints(day: Int, month: Int, year: Int): LiveData<Int>

    @Transaction
    open fun insertGoalCompletedIfNotExist(goal: CompletedGoal) {
        goalIsDone(goal.day, goal.month, goal.year, goal.goalid)
        var newCompleted = goalIsDone(goal.day, goal.month, goal.year, goal.goalid)
        if (newCompleted == null) {
            var newCompleted = CompletedGoal(goal.goalid, goal.title, goal.points, goal.thropy, goal.day, goal.month, goal.year, goal.hour, goal.minutes, goal.seconds)
            insertGoalsDone(newCompleted)
        }
    }
}

