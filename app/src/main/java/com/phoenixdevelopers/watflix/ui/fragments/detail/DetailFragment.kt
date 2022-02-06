package com.phoenixdevelopers.watflix.ui.fragments.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.phoenixdevelopers.watflix.databinding.FragmentDetailBinding
import com.phoenixdevelopers.watflix.model.Movie
import com.phoenixdevelopers.watflix.ui.fragments.detail.adapter.SimilarAdapter
import com.phoenixdevelopers.watflix.utils.Response
import com.phoenixdevelopers.watflix.utils.getImageUrl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var moviesAdapter: SimilarAdapter

    private var _binding: FragmentDetailBinding? = null

    private val binding get() = _binding!!

    private val detailViewModel: DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, bundle: Bundle?
    ): View {

        _binding = FragmentDetailBinding
            .inflate(inflater, container, false);

        return binding.root

    }

    override fun onViewCreated(view: View, bundle: Bundle?) {
        super.onViewCreated(view, bundle)

        initListAdapter()

        setupRecyclerView()

        initClickListener()

        initDetailObserver()

        initMoviesObserver()
    }

    private fun initListAdapter() {

        moviesAdapter = SimilarAdapter { movie ->

            detailViewModel.getMovieData(movie.id)

        }

    }

    private fun setupRecyclerView() {

        with(binding.moviesRecyclerView) {
            adapter = moviesAdapter
        }

    }

    private fun initClickListener() {

        binding.backButton.setOnClickListener {

            findNavController().navigateUp()
        }
    }

    private fun initDetailObserver() {

        viewLifecycleOwner.lifecycleScope.launch {

            detailViewModel.movieDetails.collect { response ->

                when (response) {

                    is Response.Loading -> {

                        binding.mainLayout.visibility = GONE
                        binding.progressBar.visibility = VISIBLE

                    }

                    is Response.Success -> {

                        setMovieDetails(response.item)

                        binding.mainLayout.visibility = VISIBLE
                        binding.progressBar.visibility = GONE

                    }

                    is Response.Error -> {

                        binding.mainLayout.visibility = GONE
                        binding.progressBar.visibility = GONE

                    }
                }

            }

        }

    }

    private fun initMoviesObserver() {

        viewLifecycleOwner.lifecycleScope.launch {

            detailViewModel.similarMovies.collect { response ->

                when (response) {

                    is Response.Loading -> {

                    }

                    is Response.Success -> {

                        moviesAdapter.submitList(response.item)
                    }

                    is Response.Error -> {

                    }
                }
            }
        }

    }

    private fun setMovieDetails(movie: Movie) {

        binding.itemYear.text = movie.year
        binding.itemName.text = movie.title
        binding.itemGenre.text = movie.genre
        binding.itemRating.text = movie.rating
        binding.itemDetails.text = movie.summary

        Glide.with(requireContext())
            .load(getImageUrl(movie.coverImage)).into(binding.itemImage)

        Glide.with(requireContext())
            .load(getImageUrl(movie.backgroundImage)).into(binding.movieCoverImage)

    }


    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null

    }
}