package com.example.rajadav.adidas_kotlin.model

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

@Dao
interface GoalDao{

    @Query("SELECT * FROM goal")
    fun loadAllGoals() : LiveData<List<Goal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGoals(goal: List<Goal>)
}