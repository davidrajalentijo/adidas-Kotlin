package com.example.rajadav.adidas_kotlin.ui.goals

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.rajadav.adidas_kotlin.MainViewModel
import com.example.rajadav.adidas_kotlin.R
import com.example.rajadav.adidas_kotlin.ViewModelFactory
import com.example.rajadav.adidas_kotlin.model.Goal

/*Fragment for the goals where we show all the goals*/
class GoalFragment : Fragment(), GoalAdapter.GoalAdapterOnClickHandler {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val rootView = inflater.inflate(R.layout.fragment_goals, container, false)

        val lv = rootView.findViewById(R.id.recyclerview_main_data) as RecyclerView
        lv.layoutManager = LinearLayoutManager(activity?.applicationContext, LinearLayout.VERTICAL, false)

        val factory = ViewModelFactory(activity!!.applicationContext)
        val model = ViewModelProviders.of(this, factory).get(MainViewModel::class.java)
        model.getGoals().observe(this, Observer<List<Goal>> {
            it?.let { data ->
                lv.adapter = GoalAdapter(data, this)
            }
        })

        return rootView
    }

    override fun onGoalClick(goal: Goal) {
        startActivity(Intent(activity?.applicationContext, DetailActivity::class.java).putExtra("id", goal.id))
    }

    companion object {

        private val ARG_SECTION_NUMBER = "section_number"

        fun newInstance(sectionNumber: Int): GoalFragment {
            val fragment = GoalFragment()
            val args = Bundle()
            args.putInt(ARG_SECTION_NUMBER, sectionNumber)
            fragment.arguments = args
            return fragment
        }
    }

}