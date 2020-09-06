package com.hsbc.pushnotifications

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.hsbc.pushnotifications.ui.main.SectionsPagerAdapter
import kotlinx.android.synthetic.main.activity_tabbed.*

class TabbedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tabbed)

        var tabLayout: TabLayout? = null
        var viewPager: ViewPager? = null
        val sharedPreferences: SharedPreferences = this.getSharedPreferences("sharedpreference",
            Context.MODE_PRIVATE)
        val instanceToken = sharedPreferences.getString("instance_token",null)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        tabLayout = findViewById<TabLayout>(R.id.tabs)
        viewPager = findViewById<ViewPager>(R.id.view_pager)
        tabLayout.setupWithViewPager(viewPager)

        tabLayout!!.addTab(tabLayout!!.newTab().setText("Transfer"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText(R.string.tab_text_2))
        tabLayout!!.addTab(tabLayout!!.newTab().setText(R.string.tab_text_3))
        tabLayout!!.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = MyAdapter(this, supportFragmentManager, tabLayout!!.tabCount)
        viewPager!!.adapter = adapter

        //viewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager!!.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {

            }
            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }
}