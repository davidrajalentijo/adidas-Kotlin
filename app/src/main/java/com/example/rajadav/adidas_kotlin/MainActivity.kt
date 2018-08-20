package com.example.rajadav.adidas_kotlin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

import com.example.rajadav.adidas_kotlin.model.Goal
import com.example.rajadav.adidas_kotlin.ui.goals.DetailActivity
import com.example.rajadav.adidas_kotlin.ui.goals.GoalAdapter
import com.example.rajadav.adidas_kotlin.ui.goals.GoalFragment
import com.example.rajadav.adidas_kotlin.ui.profile.ProfileFragment
import kotlinx.android.synthetic.main.activity_main.*

/* MainActivity to manage the GoalFragment and ProfileFragment */
class MainActivity : AppCompatActivity(), GoalAdapter.GoalAdapterOnClickHandler {

    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)
        viewpager.adapter = mSectionsPagerAdapter
        sliding_tabs.setupWithViewPager(viewpager)
    }

    override fun onGoalClick(goal: Goal) {
        startActivity(Intent(this@MainActivity, DetailActivity::class.java).putExtra("id", goal.id))
    }

    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            if (position == 0) {
                return GoalFragment.newInstance(position + 1)
            } else {
                return ProfileFragment.newInstance(position + 1)
            }
        }

        override fun getCount(): Int {
            return 2
        }

        override fun getPageTitle(position: Int): CharSequence? {
            if (position == 0) {
                return resources.getString(R.string.tab_text_1)
            } else {
                return resources.getString(R.string.tab_text_2)
            }
        }
    }

}


