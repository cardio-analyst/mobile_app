package com.example.laboratory_research.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2

/**
 * Adapter for [ViewPager2] on [LaboratoryResearchFragment] tab
 * contains list of [LaboratoryResearchRecordFragment]
 * based on [FragmentStateAdapter]
 * @param fragmentManager [FragmentManager]
 * @param lifecycle [Lifecycle]
 */
class LaboratoryResearchAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    private val fragmentList: MutableList<LaboratoryResearchRecordFragment> = mutableListOf()

    fun addFragment(laboratoryResearchRecordFragment: LaboratoryResearchRecordFragment) {
        fragmentList.add(laboratoryResearchRecordFragment)
    }

    fun getFragment(position: Int) = fragmentList[position]

    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}