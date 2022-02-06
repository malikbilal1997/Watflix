package com.phoenixdevelopers.watflix.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.intuit.sdp.R.dimen._12sdp
import com.intuit.sdp.R.dimen._4sdp
import com.phoenixdevelopers.watflix.R
import com.phoenixdevelopers.watflix.databinding.FragmentHomeBinding
import com.phoenixdevelopers.watflix.model.Movie
import com.phoenixdevelopers.watflix.ui.fragments.home.adapter.MoviesAdapter
import com.phoenixdevelopers.watflix.utils.Response
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var areMoviesShownAsGrid = true

    private lateinit var moviesAdapter: MoviesAdapter

    private lateinit var mLayoutManager: LayoutManager

    private var moviesList: List<Movie> = emptyList()

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

        setupRecyclerView()

        initMoviesObserver()

    }

    private fun setupSearchList() {


    }

    private fun setupListAdapter() {

        moviesAdapter = MoviesAdapter(areMoviesShownAsGrid).also {
            it.submitList(moviesList)
        }
    }

    private fun setupRecyclerView() {

        setupListAdapter()

        with(binding.moviesRecyclerView) {

            adapter = moviesAdapter

            layoutManager = if (::mLayoutManager.isInitialized) {

                setPadding(0, 0, 0, 0)

                mLayoutManager

            } else {

                setPadding(_12sdp, 0, 0, _4sdp)

                GridLayoutManager(requireContext(), 2)
            }
        }
    }

    private fun initClickListener() {

        binding.changeLayout.setOnClickListener {

            changeLayoutButton()
            changeLayoutManager()
        }
    }

    private fun changeLayoutButton() {

        if (areMoviesShownAsGrid) {

            binding.changeLayout.setImageResource(R.drawable.ic_grid_view)

        } else {

            binding.changeLayout.setImageResource(R.drawable.ic_view_list)
        }

    }

    private fun changeLayoutManager() {

        mLayoutManager = if (areMoviesShownAsGrid) {

            LinearLayoutManager(
                requireContext()
            )

        } else {
            GridLayoutManager(
                requireContext(), 2
            )
        }

        areMoviesShownAsGrid = !areMoviesShownAsGrid

        setupRecyclerView()
    }

    private fun initMoviesObserver() {

        lifecycleScope.launch {

            homeViewModel.movieResponse.collect { response ->

                when (response) {

                    is Response.Loading -> {

                        binding.progressBar.visibility = VISIBLE
                    }

                    is Response.Success -> {

                        moviesList = response.item

                        moviesAdapter.submitList(moviesList)

                        binding.progressBar.visibility = GONE
                    }

                    is Response.Error -> {

                        binding.progressBar.visibility = GONE

                    }
                }
            }
        }
    }

}