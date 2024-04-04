package com.example.submissiongithub

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissiongithub.databinding.FragmentFollowBinding

class FollowerFragment : Fragment() {

    private lateinit var binding: FragmentFollowBinding
    private lateinit var adapter: UserAdapter


    private var position: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val receivedData = arguments?.getString(
            ARG_USERNAME
        ) ?: "sappawrdn"

        arguments?.let{
            position = it.getInt(ARG_POSITION)
        }

        val mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowerViewModel::class.java)

        mainViewModel.followData.observe(viewLifecycleOwner) { items ->
            adapter.submitList(items)
        }

        mainViewModel.folliwData.observe(viewLifecycleOwner) { items ->
            adapter.submitList(items)
        }

        adapter = UserAdapter()

        showRecyclerList()


        mainViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        if (position == 0){
            showLoading(true)
            mainViewModel.followUser(receivedData)
        }else{
            showLoading(true)
            mainViewModel.folliwUser(receivedData)
        }



    }

    private fun showRecyclerList() {
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvFollow.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.rvFollow.addItemDecoration(itemDecoration)
        binding.rvFollow.adapter = adapter
    }


    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBarFollow.visibility = View.VISIBLE
        } else {
            binding.progressBarFollow.visibility = View.GONE
        }
    }

    companion object {
        const val ARG_USERNAME = "username"
        const val ARG_POSITION = "position"

    }
}