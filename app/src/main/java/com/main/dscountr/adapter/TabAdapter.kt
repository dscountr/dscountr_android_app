package com.main.dscountr.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.main.dscountr.fragment.FirstFragment
import com.main.dscountr.fragment.FourthFragment
import com.main.dscountr.fragment.SecondFragment
import com.main.dscountr.fragment.ThirdFragment


class TabAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                FirstFragment()
            }
            1 -> {
                SecondFragment()
            }
            2 -> {
                return ThirdFragment()
            }
            else -> {
                return FourthFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 4
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "First Tab"
            1 -> "Second Tab"
            2 -> "Third Tab"
            else -> {
                return "Fourth Tab"
            }
        }
    }
}