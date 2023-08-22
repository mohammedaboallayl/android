package com.example.myapplication.RecAdapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdabter(fragment: Fragment,fragments:Array<Fragment>) : FragmentStateAdapter(fragment) {
    private var ArrayF:Array<Fragment>

    init {
        ArrayF=fragments
    }
    override fun getItemCount(): Int {
        return ArrayF.size
    }

    override fun createFragment(position: Int): Fragment {
        return this.ArrayF[position]
    }
}