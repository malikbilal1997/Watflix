package com.phoenixdevelopers.watflix.ui.fragments.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
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

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        bundle: Bundle?
    ): View {

        _binding = FragmentHomeBinding
            .inflate(inflater, container, false);

        return binding.root

    }

    override fun onViewCreated(view: View, bundle: Bundle?) {
        super.onViewCreated(view, bundle)

        initClickListener()

        setupRecyclerView()

        initMoviesObserver()

        setupSearchViewList()

    }


    private fun setupListAdapter() {

        moviesAdapter = MoviesAdapter(areMoviesShownAsGrid) { movie ->

            navigateToDetail(movie.id)

        }.also {
            it.submitList(moviesList)
        }
    }

    private fun setupRecyclerView() {

        setupListAdapter()

        changeListPadding()

        with(binding.moviesRecyclerView) {

            adapter = moviesAdapter

            layoutManager = if (::mLayoutManager.isInitialized) {
                mLayoutManager

            } else {
                GridLayoutManager(requireContext(), 2)
            }
        }
    }

    private fun initClickListener() {

        binding.changeButton.setOnClickListener {

            changeListPadding()

            changeLayoutButton()

            changeLayoutManager()
        }
    }

    private fun setupSearchViewList() {

        binding.searchEdit.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(editable: Editable?) = Unit

            override fun beforeTextChanged(
                sequence: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) = Unit

            override fun onTextChanged(
                sequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {

                if (sequence != null) {

                    filterMoviesList(sequence)

                } else {

                    moviesAdapter.submitList(moviesList)
                }

            }

        })

    }


    private fun changeLayoutButton() {

        if (areMoviesShownAsGrid) {

            binding.changeButton.setImageResource(R.drawable.ic_grid_view)

        } else {

            binding.changeButton.setImageResource(R.drawable.ic_view_list)
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

        viewLifecycleOwner.lifecycleScope.launch {

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

    private fun changeListPadding() {

        if (areMoviesShownAsGrid) {

            applyGridPadding()

        } else {

            applyListPadding()
        }

    }

    private fun applyListPadding() {

        binding.moviesRecyclerView.setPadding(
            0, 0, 0, 0
        )
    }

    private fun applyGridPadding() {

        binding.moviesRecyclerView.setPadding(
            resources.getDimensionPixelSize(_12sdp), 0,
            resources.getDimensionPixelSize(_4sdp), 0
        )

    }

    private fun navigateToDetail(movieId: String) {

        val action = HomeFragmentDirections
            .actionHomeFragmentToDetailFragment(movieId)

        findNavController().navigate(action)
    }

    private fun filterMoviesList(query: CharSequence) {

        val filteredList = moviesList.filter {

            it.title.lowercase().contains(query.toString().lowercase())
        }

        moviesAdapter.submitList(filteredList)

    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}