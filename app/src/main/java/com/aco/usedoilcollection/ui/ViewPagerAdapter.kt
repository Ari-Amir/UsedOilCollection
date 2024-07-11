package com.aco.usedoilcollection.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.aco.usedoilcollection.fragments.InputFragment
import com.aco.usedoilcollection.fragments.StatisticsFragment
import com.aco.usedoilcollection.fragments.LocationsFragment
import com.aco.usedoilcollection.fragments.AccountFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> InputFragment()
            1 -> StatisticsFragment()
            2 -> LocationsFragment()
            3 -> AccountFragment()
            else -> Fragment()
        }
    }
}
