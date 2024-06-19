package com.example.academyinformationapplication.ui.main

import AcademyAdapter
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.academyinformationapplication.databinding.RecyclerviewBinding

class EntireTab : Fragment() {
    private val TAG = "EntireTab"
    private lateinit var mainViewModel: MainViewModel
    private var _binding: RecyclerviewBinding? = null
    private val binding get() = _binding!!
    private lateinit var academyAdapter: AcademyAdapter

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RecyclerviewBinding.inflate(inflater, container, false)
        val root: View = binding.root

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
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
        mainViewModel.groupedData.observe(viewLifecycleOwner, Observer {
            it?.let {
                academyAdapter.updateAcademy(it)
                academyAdapter.notifyDataSetChanged()
            }
        })

        mainViewModel.academyError.observe(viewLifecycleOwner, Observer { isError ->
            Log.d(TAG, "movieLoadError=$isError")
        })

        mainViewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            isLoading?.let {
                if (it) {
                    Log.d(TAG, "loading=$it")
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}