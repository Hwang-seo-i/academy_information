package com.example.academyinformationapplication.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.academyinformationapplication.data.model.AcademyTeachingProcess
import com.example.academyinformationapplication.databinding.RecyclerviewBinding
import com.example.academyinformationapplication.ui.adapter.FavoriteAdapter

class FavoriteTab : Fragment() {

    private var _binding: RecyclerviewBinding? = null
    private val binding get() = _binding!!
    private lateinit var favoriteAdapter: FavoriteAdapter
    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RecyclerviewBinding.inflate(inflater, container, false)
        val root: View = binding.root

        favoriteAdapter = FavoriteAdapter(arrayListOf())

        binding.recyclerView.apply {
            adapter = favoriteAdapter
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        mainViewModel.favoriteList.observe(viewLifecycleOwner) {
            favoriteAdapter.updateFavorites(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun updateFavoriteList(position: Int?, isFavorite: Boolean) {
        position?.let {
            val academy = favoriteAdapter.academyList[position]
            if (isFavorite) {
                mainViewModel.addFavorite(academy)
            } else {
                mainViewModel.removeFavorite(academy)
            }
        }
    }
}
