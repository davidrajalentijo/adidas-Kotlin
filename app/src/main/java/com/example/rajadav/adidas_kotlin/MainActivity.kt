package com.example.rajadav.adidas_kotlin

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.LinearLayout
import com.example.rajadav.adidas_kotlin.R.id.recyclerview_main_data
import com.example.rajadav.adidas_kotlin.model.Goal
import com.example.rajadav.adidas_kotlin.model.Items


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val lv = findViewById(recyclerview_main_data) as RecyclerView
        lv.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        val factory  = ViewModelFactory(this)
        val model= ViewModelProviders.of(this, factory).get(MainViewModel::class.java)
        model.getGoals().observe(this, Observer<List<Goal>>{data ->
            lv.adapter = GoalAdapter(data!!)
        })
    }

}


