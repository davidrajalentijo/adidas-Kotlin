package com.example.rajadav.adidas_kotlin.ui.profile

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.rajadav.adidas_kotlin.MainViewModel
import com.example.rajadav.adidas_kotlin.R
import com.example.rajadav.adidas_kotlin.ViewModelFactory
import com.example.rajadav.adidas_kotlin.model.CompletedGoal
import kotlinx.android.synthetic.main.fragment_profile.view.*
import java.util.*

class ProfileFragment: Fragment(), ProfileAdapter.ProfileAdapterOnClickHandler{

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val rootView = inflater.inflate(R.layout.fragment_profile, container, false)

        val lv = rootView.findViewById(R.id.recyclerview_main_data) as RecyclerView
        lv.layoutManager = LinearLayoutManager(activity?.applicationContext, LinearLayout.VERTICAL, false)

        val factory  = ViewModelFactory(activity!!.applicationContext)
        val model= ViewModelProviders.of(this, factory).get(MainViewModel::class.java)
        model.getGoalDone().observe(this, Observer<List<CompletedGoal>>{
            it?.let {data ->
                lv.adapter = ProfileAdapter(data, this)
            }
        })

        model.getTotalPoints().observe(this, Observer {
            rootView.tv_total_points.text = rootView.resources.getString(R.string.total_points, it?: 0)
        })

        val cal: Calendar = Calendar.getInstance()
        model.getTodayPoints(cal.get(Calendar.DAY_OF_MONTH),(cal.get(Calendar.MONTH) +1),cal.get(Calendar.YEAR)).observe(this, Observer {
            rootView.tv_day_points.text = rootView.resources.getString(R.string.today_points, it?: 0)
        })

        return rootView
    }

    override fun onGoalClick(goal: CompletedGoal) {
        Log.d("ProfileFragment", "Goal clicked")
    }

    companion object {

        private val ARG_SECTION_NUMBER = "section_number"

        fun newInstance(sectionNumber: Int): ProfileFragment {
            val fragment = ProfileFragment()
            val args = Bundle()
            args.putInt(ARG_SECTION_NUMBER, sectionNumber)
            fragment.arguments = args
            return fragment
        }
    }

}