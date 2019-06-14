package com.main.dscountr.view

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.main.dscountr.R
import com.main.dscountr.adapter.TabAdapter
import com.main.dscountr.fragment.FirstFragment
import com.main.dscountr.fragment.FourthFragment
import com.main.dscountr.fragment.SecondFragment
import com.main.dscountr.fragment.ThirdFragment
import kotlinx.android.synthetic.main.home_activity.*

class HomeActivity : AppCompatActivity() {

    lateinit var toolbar: ActionBar

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_first -> {
                toolbar.title = "First"
                val firstFragment = FirstFragment.newInstance()
                openFragment(firstFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_second -> {
                toolbar.title = "Second"
                val secondFragment = SecondFragment.newInstance()
                openFragment(secondFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_third -> {
                toolbar.title = "Second"
                val thirdFragment = ThirdFragment.newInstance()
                openFragment(thirdFragment)
                return@OnNavigationItemSelectedListener true
            }

            R.id.navigation_fourth -> {
                toolbar.title = "Second"
                val fourthFragment = FourthFragment.newInstance()
                openFragment(fourthFragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        toolbar = supportActionBar!!
        val bottomNavigation: BottomNavigationView = findViewById(R.id.navigationView)
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
}