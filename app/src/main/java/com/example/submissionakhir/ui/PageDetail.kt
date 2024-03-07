package com.example.submissionakhir.ui

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionakhir.databinding.FragmentPageDetailBinding
import com.example.submissionakhir.viewmodel.DetailViewModel
import com.example.submissionakhir.viewmodel.ViewModelFactory
import com.example.submissionakhir.adapter.HomeAdapter


class PageDetail : Fragment() {


    private var _binding: FragmentPageDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<DetailViewModel>{
        ViewModelFactory.getInstance(requireActivity().application as Application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPageDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val names = arguments?.getString(ARG_NAME) ?: ""
        val index = arguments?.getInt(ARG_SECTION_NUMBER)

        viewModel.getFollowers(names)
        viewModel.getFollowing(names)

        val layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL,false)
        binding.rvFollowing.layoutManager = layoutManager
        viewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }
        if (index == 1) {
            viewModel.listFollowers.observe(viewLifecycleOwner){
                val adapter = HomeAdapter(null)
                adapter.sendCategory(it)

                binding.rvFollowing.adapter = adapter
            }
        }else {
            viewModel.listFollowing.observe(viewLifecycleOwner) {
                val adapter = HomeAdapter(null)
                adapter.sendCategory(it)
                binding.rvFollowing.adapter = adapter
            }
        }

    }
    companion object {
        const val ARG_NAME = ""
        const val ARG_SECTION_NUMBER = "0"
    }
    private fun showLoading(isLoading: Boolean) {
        if (isLoading){
            binding.progressFollowing.visibility = View.VISIBLE
        }else {
            binding.progressFollowing.visibility = View.GONE
        }

    }

}