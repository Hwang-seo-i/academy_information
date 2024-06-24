package com.example.academyinformationapplication.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.academyinformationapplication.databinding.RecyclerviewBinding
import com.example.academyinformationapplication.ui.adapter.AcademyAdapter

class FavoriteTab : Fragment() {

    private var _binding: RecyclerviewBinding? = null
    private val binding get() = _binding!!
    private lateinit var academyAdapter: AcademyAdapter
    private lateinit var mainViewModel: MainViewModel
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RecyclerviewBinding.inflate(inflater, container, false)
        val root: View = binding.root

        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        academyAdapter = AcademyAdapter(mutableListOf())

        binding.recyclerView.apply {
            adapter = academyAdapter
        }

        return root
    }

    override fun onResume() {
        super.onResume()
        sharedPreferences = requireContext().getSharedPreferences("favorites", Context.MODE_PRIVATE)
        mainViewModel.setPreferences(sharedPreferences)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observeViewModel() {
        mainViewModel.favoriteList.observe(viewLifecycleOwner) { favoriteList ->
            favoriteList?.let {
                println(">>>> Favorite list updated: $it")
                academyAdapter.updateAcademy(it)
                academyAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
