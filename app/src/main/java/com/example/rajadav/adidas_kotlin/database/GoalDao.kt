package com.example.rajadav.adidas_kotlin.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.example.rajadav.adidas_kotlin.model.Goal

@Dao
interface GoalDao{

    @Query("SELECT * FROM goal")
    fun loadAllGoals() : LiveData<List<Goal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGoals(goal: List<Goal>)

    @Query("SELECT * FROM goal WHERE id =:id")
    fun getGoalById(id : Int): LiveData<Goal>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateGoal(goal: Goal)
}

