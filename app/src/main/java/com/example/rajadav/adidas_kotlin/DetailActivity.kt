package com.example.rajadav.adidas_kotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.TextView

class DetailActivity : AppCompatActivity(){

    lateinit internal var titlegoal: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        titlegoal = findViewById<View>(R.id.tv_title_goal) as TextView
        titlegoal.text = intent.extras.getString("title")
    }

}