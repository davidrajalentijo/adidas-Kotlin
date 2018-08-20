package com.example.rajadav.adidas_kotlin.ui.goals

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rajadav.adidas_kotlin.R
import com.example.rajadav.adidas_kotlin.model.Goal
import kotlinx.android.synthetic.main.list_goals.view.*

/*Adapter to manage the list of Goals */
class GoalAdapter(val goals: List<Goal>, val listener: GoalAdapterOnClickHandler) : RecyclerView.Adapter<GoalAdapter.GoalAdapterViewHolder>() {

    override fun getItemCount(): Int {
        return goals.size
    }

    interface GoalAdapterOnClickHandler {
        fun onGoalClick(goal: Goal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalAdapterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_goals, parent, false)
        return GoalAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: GoalAdapterViewHolder?, position: Int) {
        holder?.bindGoal(position)
    }

    inner class GoalAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        fun bindGoal(position: Int) {
            val goal = goals.get(position)
            itemView.tvtitle.text = goal.title
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listener.onGoalClick(goals.get(position))
        }

    }

}