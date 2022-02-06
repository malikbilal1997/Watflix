package com.phoenixdevelopers.watflix.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.phoenixdevelopers.watflix.databinding.FragmentHomeBinding
import com.phoenixdevelopers.watflix.ui.fragments.home.adapter.MoviesAdapter
import com.phoenixdevelopers.watflix.utils.Response
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var areMoviesShownAsGrid = true

    private lateinit var moviesAdapter: MoviesAdapter

    private lateinit var binding: FragmentHomeBinding

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        bundle: Bundle?
    ): View {

        binding = FragmentHomeBinding
            .inflate(inflater, container, false);

        return binding.root

    }

    override fun onViewCreated(view: View, bundle: Bundle?) {
        super.onViewCreated(view, bundle)

        initClickListener()

        setupListAdapter()

        initMoviesObserver()

    }

    private fun setupListAdapter() {

        moviesAdapter = MoviesAdapter(areMoviesShownAsGrid)

        with(binding.moviesRecyclerView) {

            adapter = moviesAdapter

            layoutManager = GridLayoutManager(
                requireContext(), 2
            )
        }
    }

    private fun changeLayoutManager() {

        areMoviesShownAsGrid = !areMoviesShownAsGrid

        if (areMoviesShownAsGrid) {

            with(binding.moviesRecyclerView) {

                layoutManager = LinearLayoutManager(
                    requireContext()
                )
            }

        } else {

            with(binding.moviesRecyclerView) {

                layoutManager = GridLayoutManager(
                    requireContext(), 2
                )
            }
        }

    }

    private fun initClickListener() {

        binding.changeLayout.setOnClickListener {
            changeLayoutManager()
        }
    }

    private fun initMoviesObserver() {

        lifecycleScope.launch {

            homeViewModel.movieResponse.collect { response ->

                when (response) {

                    is Response.Loading -> {

                        binding.progressBar.visibility = View.VISIBLE

                    }

                    is Response.Success -> {

                        moviesAdapter.submitList(response.item)
                        binding.progressBar.visibility = View.GONE
                    }

                    is Response.Error -> {

                        binding.progressBar.visibility = View.GONE

                    }
                }
            }
        }
    }

}