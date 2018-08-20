package com.example.rajadav.adidas_kotlin.ui.profile

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rajadav.adidas_kotlin.R
import com.example.rajadav.adidas_kotlin.model.CompletedGoal
import com.example.rajadav.adidas_kotlin.model.Goal
import kotlinx.android.synthetic.main.list_profile.view.*

/* Adapter to manage the list of goals done */
class ProfileAdapter(val goals: List<CompletedGoal>, val listener: ProfileAdapterOnClickHandler) : RecyclerView.Adapter<ProfileAdapter.ProfileAdapterViewHolder>() {

    override fun getItemCount(): Int {
        return goals.size
    }

    interface ProfileAdapterOnClickHandler {
        fun onGoalClick(goal: CompletedGoal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileAdapterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_profile, parent, false)
        return ProfileAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProfileAdapterViewHolder?, position: Int) {
        holder?.bindGoal(position)
    }

    inner class ProfileAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        fun bindGoal(position: Int) {
            val goal = goals.get(position)
            itemView.tv_title_data.text = goal.title
            itemView.tv_points.text = goal.points.toString()
            itemView.tv_date.text = itemView.resources.getString(R.string.final_date, goal.day, goal.month, goal.year)

            when (goal.thropy) {
                Goal.BRONZE_REWARD -> itemView.imageReward.setImageResource(R.drawable.bronzemedal)
                Goal.GOLD_REWARD -> itemView.imageReward.setImageResource(R.drawable.goldmedal)
                Goal.SILVER_REWARD -> itemView.imageReward.setImageResource(R.drawable.silvermedal)
                Goal.ZOMBIE_REWARD -> itemView.imageReward.setImageResource(R.drawable.if__zombie_rising_1573300)
            }
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listener.onGoalClick(goals.get(position))
        }

    }

}