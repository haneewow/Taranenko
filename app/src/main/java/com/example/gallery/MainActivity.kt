package com.example.gallery

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.gallery.databinding.ActivityMainBinding
import com.example.gallery.ui.BaseFragment
import com.example.gallery.ui.InfoBottomSheetFragment
import com.example.gallery.ui.pages.latest.LatestFragment
import com.example.gallery.ui.adapter.ViewPagerAdapter
import com.example.gallery.util.TitleCreator
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity(R.layout.activity_main),
    BaseFragment.OnFragmentInteractionListener {

    private val binding: ActivityMainBinding by viewBinding()

    private val pages = TitleCreator().create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.elevation = 0F
        binding.viewPager.adapter = ViewPagerAdapter(pages, this)
        TabLayoutMediator(
            binding.tab, binding.viewPager
        ) { tab: TabLayout.Tab, position: Int ->
            tab.text = pages[position]
        }.attach()
    }

    override fun sendNote(bundle: Bundle) {
        InfoBottomSheetFragment.newInstance(bundle).apply {
            show(supportFragmentManager, null)
        }
    }
}