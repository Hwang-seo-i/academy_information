package com.example.academyinformationapplication.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.academyinformationapplication.databinding.FragmentDetailHomeBinding
import com.example.academyinformationapplication.ui.adapter.DetailAdapter

class HomeFragment : Fragment() {
    private val TAG = "HomeFragment"
    private lateinit var detailViewModel: DetailViewModel
    private var _binding: FragmentDetailHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var detailAdapter: DetailAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        detailViewModel = ViewModelProvider(requireActivity()).get(DetailViewModel::class.java)

        detailAdapter = DetailAdapter(emptyList())
        binding.detailRecyclerView.adapter = detailAdapter

        observeViewModel()

        return root
    }

    private fun observeViewModel() {
        detailViewModel.academyData.observe(viewLifecycleOwner, Observer { data ->
            data?.let {
                detailAdapter.updateDetailList(it)
            }
        })

        detailViewModel.academyError.observe(viewLifecycleOwner, Observer { isError ->
            Log.d(TAG, "academyLoadError=$isError")
        })

        detailViewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            isLoading?.let {
                Log.d(TAG, "loading=$it")
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
