package com.example.academyinformationapplication.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.academyinformationapplication.databinding.RecyclerviewBinding
import com.example.academyinformationapplication.ui.adapter.AcademyAdapter

class EntireTab : Fragment() {
    private val TAG = "EntireTab"

    private var _binding: RecyclerviewBinding? = null
    private val binding get() = _binding!!
    private lateinit var academyAdapter: AcademyAdapter
    private lateinit var mainViewModel: MainViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RecyclerviewBinding.inflate(inflater, container, false)
        val root: View = binding.root

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

//        // SharedPreferences 객체 가져와서 ViewModel에 설정
//        val preferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
//        mainViewModel.setPreferences(preferences)

        mainViewModel.refresh()

        academyAdapter = AcademyAdapter(arrayListOf())

        binding.recyclerView.apply {
            adapter = academyAdapter
        }

        observeViewModel()

        return root
    }

    @SuppressLint("NotifyDataSetChanged")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun observeViewModel() {
        mainViewModel.groupedData.observe(viewLifecycleOwner) { it ->
            it?.let {
                academyAdapter.updateAcademy(it)
                academyAdapter.notifyDataSetChanged()
            }
        }

        mainViewModel.academyError.observe(viewLifecycleOwner) { isError ->
            Log.d(TAG, "academyLoadError=$isError")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
