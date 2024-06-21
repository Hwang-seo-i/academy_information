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

    companion object {
        const val DETAIL_REQUEST_CODE = 1001
    }

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

    // 디테일 액티비티에서 전달된 데이터 처리
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == DETAIL_REQUEST_CODE && resultCode == RESULT_OK) {
            val likePosition = data?.getIntExtra("likePosition", -1)
            val isLiked = data?.getBooleanExtra("isLiked", false)

            // FavoriteTab Fragment에 데이터 전달
            val fragment = supportFragmentManager.findFragmentByTag("${1}") as FavoriteTab
            if (isLiked != null) {
                fragment.updateFavoriteList(likePosition, isLiked)
            }
        }
    }
}
