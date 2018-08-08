package com.example.rajadav.adidas_kotlin

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rajadav.adidas_kotlin.model.Goal
import kotlinx.android.synthetic.main.list_goals.view.*

class GoalAdapter(val goals: List<Goal>, val listener: GoalAdapterOnClickHandler): RecyclerView.Adapter<GoalAdapter.GoalAdapterViewHolder>(){

    override fun getItemCount(): Int {
        return goals.size
    }

     interface GoalAdapterOnClickHandler{
         fun onGoalClick(goal: Goal)
    }

     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalAdapterViewHolder {
         val view = LayoutInflater.from(parent.context).inflate(R.layout.list_goals, parent, false)
         return GoalAdapterViewHolder(view)
     }

     override fun onBindViewHolder(holder: GoalAdapterViewHolder?, position: Int) {
         holder?.bindGoal(position)
     }

    inner class GoalAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{

        fun bindGoal(position: Int){
            val goal = goals.get(position)
            itemView.tvtitle.text = goal.title
        }

        override fun onClick(v: View?) {
            val position = v?.tag as Int
            val goal = goals.get(position)
                listener.onGoalClick(goal)
        }

    }

}