package com.example.rajadav.adidas_kotlin

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.LinearLayout
import com.example.rajadav.adidas_kotlin.R.id.recyclerview_main_data
import com.example.rajadav.adidas_kotlin.model.Goal
import com.example.rajadav.adidas_kotlin.ui.goals.DetailActivity
import com.example.rajadav.adidas_kotlin.ui.goals.GoalAdapter


class MainActivity : AppCompatActivity(), GoalAdapter.GoalAdapterOnClickHandler {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val lv = findViewById(recyclerview_main_data) as RecyclerView
        lv.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        val factory  = ViewModelFactory(this)
        val model= ViewModelProviders.of(this, factory).get(MainViewModel::class.java)

        model.getGoals().observe(this, Observer<List<Goal>>{
            it?.let {data ->
                lv.adapter = GoalAdapter(if (data != null) data else throw NullPointerException("Expression 'data' must not be null"), this)
            }

            //lv.adapter = GoalAdapter(if (data != null) data else throw NullPointerException("Expression 'data' must not be null"), this)
        })
    }

    override fun onGoalClick(goal: Goal){
        Log.d("mainActivity", "clicking")
        startActivity(Intent(this@MainActivity, DetailActivity::class.java).putExtra("title", goal.id))
    }

}


