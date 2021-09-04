package com.example.gallery.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.gallery.ui.pages.best.BestFragment
import com.example.gallery.ui.pages.hot.HotFragment
import com.example.gallery.ui.pages.latest.LatestFragment

class ViewPagerAdapter(
    private val pages: List<String>,
    activity: FragmentActivity
) : FragmentStateAdapter(activity) {

    override fun getItemCount() = pages.size

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            Category.LATEST.ordinal -> LatestFragment()
            Category.TOP.ordinal -> BestFragment()
            Category.HOT.ordinal -> HotFragment()
            else -> LatestFragment()
        }
    }
}

enum class Category {
    LATEST, TOP, HOT
}