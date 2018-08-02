package com.example.rajadav.adidas_kotlin

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rajadav.adidas_kotlin.model.Goal
import kotlinx.android.synthetic.main.list_goals.view.*

class GoalAdapter(val goals: List<Goal>): RecyclerView.Adapter<GoalAdapter.GoalAdapterViewHolder>(){

    override fun getItemCount(): Int {
        return goals.size
    }

     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalAdapterViewHolder {
         val view = LayoutInflater.from(parent.context).inflate(R.layout.list_goals, parent, false)
         return GoalAdapterViewHolder(view)
     }

     override fun onBindViewHolder(holder: GoalAdapterViewHolder?, position: Int) {
         holder?.bindGoal(position)
     }

    inner class GoalAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bindGoal(position: Int){
            val goal = goals.get(position)
            itemView.tvtitle.text = goal.title
            itemView.setOnClickListener { v:View->
                Log.d("item clicked", "clicked")
            }
        }
    }

}