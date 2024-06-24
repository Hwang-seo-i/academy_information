package com.example.academyinformationapplication.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.academyinformationapplication.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

private const val NUM_PAGE = 2

class MainActivity : AppCompatActivity() {

    private val tabTextList = arrayListOf("전체", "좋아요 목록")
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // viewPager 초기화
        viewPager = binding.viewPager
        viewPager.adapter = ScreenSlidePagerAdapter(this)
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        // 뷰페이저와 탭레이아웃을 붙임
        TabLayoutMediator(binding.tabs, viewPager) { tab, position ->
            tab.text = tabTextList[position]
        }.attach()
    }

    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = NUM_PAGE

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> EntireTab()
                else -> FavoriteTab()
            }
        }
    }
}
