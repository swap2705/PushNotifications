package com.hsbc.pushnotifications

import android.content.Context;
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class MyAdapter(private val myContext: Context, fm: FragmentManager, internal var totalTabs: Int) : FragmentPagerAdapter(fm) {

    // this is for fragment tabs
    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                return TransferFragment()
            }
            1 -> {
                return AddBeneficiaryFragment()
            }
            2 -> {
                return UpdatePersonalDetailsFragment()
            }
            else -> throw IllegalStateException("position is invalid for this viewpager")
        }
    }

    // this counts total number of tabs
    override fun getCount(): Int {
        return totalTabs
    }

    override fun getPageTitle(position: Int): CharSequence? {
        var title: String? = null
        if(position == 0){
            title = "Transfer"
        }else if(position == 1){
            title = "Add beneficiary"
        }else if(position == 2){
            title = "Update your details"
        }
        return title
    }
}