package com.example.academyinformationapplication.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.academyinformationapplication.data.model.AcademyTeachingProcess
import com.example.academyinformationapplication.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayoutMediator

private const val DETAIL_PAGE = 2

class DetailActivity : AppCompatActivity() {

    private val tabTextList = arrayListOf("상세정보", "리뷰")
    private lateinit var viewPager: ViewPager2
    private lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ViewModel 초기화
        detailViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        // Main에서 인텐트로 전달된 데이터 가져오기
        val data = intent.getSerializableExtra("data") as AcademyTeachingProcess
        val academyName = data.academyName
        val teachingProcess = data.teachingProcess

        // 데이터를 ViewModel에 전달하고 갱신 요청
        detailViewModel.refresh(data)

        // 뷰페이저 초기화
        viewPager = binding.viewPager
        viewPager.adapter = ScreenSlidePagerAdapter(this)
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        // 탭레이아웃과 뷰페이저 연결
        TabLayoutMediator(binding.tabs, viewPager) { tab, position ->
            tab.text = tabTextList[position]
        }.attach()

        // UI에 데이터 표시
        binding.academyName.text = academyName
        binding.teachingProcess.text = teachingProcess
        binding.academyAddress.text = data.academyAddress
    }

    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = DETAIL_PAGE

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> HomeFragment()
                else -> SubjectFragment()
            }
        }
    }
}
