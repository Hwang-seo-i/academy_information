package com.example.academyinformationapplication.ui.detail

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.academyinformationapplication.R
import com.example.academyinformationapplication.data.model.AcademyTeachingProcess
import com.example.academyinformationapplication.databinding.ActivityDetailBinding
import com.example.academyinformationapplication.ui.main.MainViewModel
import com.google.android.material.tabs.TabLayoutMediator

private const val DETAIL_PAGE = 2

class DetailActivity : AppCompatActivity() {

    private val tabTextList = arrayListOf("상세정보", "리뷰")
    private lateinit var viewPager: ViewPager2
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var mainViewModel: MainViewModel
    private lateinit var favoriteIcon: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener {
            finish()
        }

        detailViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        val data = intent.getSerializableExtra("data") as AcademyTeachingProcess

        // Preferences 설정
        mainViewModel.setPreferences(getSharedPreferences("favorites", MODE_PRIVATE))

        detailViewModel.refresh(data)

        viewPager = binding.viewPager
        viewPager.adapter = ScreenSlidePagerAdapter(this)
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        TabLayoutMediator(binding.tabs, viewPager) { tab, position ->
            tab.text = tabTextList[position]
        }.attach()

        binding.academyName.text = data.academyName
        binding.teachingProcess.text = data.teachingProcess
        binding.academyAddress.text = data.academyAddress

        favoriteIcon = binding.favoriteIcon

        var isFavorite = getCurrentFavoriteState(data)
        updateFavoriteIcon(data)

        favoriteIcon.setOnClickListener {
            isFavorite = !isFavorite
            if (isFavorite) {
                mainViewModel.addFavorite(data)
            } else {
                mainViewModel.removeFavorite(data)
            }
            updateFavoriteIcon(data)
        }
    }

    private fun getCurrentFavoriteState(data: AcademyTeachingProcess): Boolean {
        val currentFavoriteState = mainViewModel.getCurrentFavoriteState(data)
        return currentFavoriteState
    }

    private fun updateFavoriteIcon(data: AcademyTeachingProcess) {
        if (getCurrentFavoriteState(data)) {
            favoriteIcon.setImageResource(R.drawable.favorite)
        } else {
            favoriteIcon.setImageResource(R.drawable.favorite_border)
        }
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
