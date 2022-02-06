package com.phoenixdevelopers.watflix.ui.fragments.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.phoenixdevelopers.watflix.databinding.FragmentDetailBinding
import com.phoenixdevelopers.watflix.ui.fragments.detail.adapter.SimilarAdapter
import com.phoenixdevelopers.watflix.utils.Response
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailFragment : Fragment() {

    private lateinit var moviesAdapter: SimilarAdapter

    private var _binding: FragmentDetailBinding? = null

    private val binding get() = _binding!!

    private val detailViewModel: DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, bundle: Bundle?
    ): View? {

        _binding = FragmentDetailBinding
            .inflate(inflater, container, false);

        return binding.root

    }

    override fun onViewCreated(view: View, bundle: Bundle?) {
        super.onViewCreated(view, bundle)

        initListAdapter()

        initClickListener()

        initDetailObserver()

        initMoviesObserver()
    }

    private fun initListAdapter() {

        moviesAdapter = SimilarAdapter()

    }

    private fun initClickListener() {

        binding.backButton.setOnClickListener {

            findNavController().navigateUp()

        }

    }

    private fun initDetailObserver() {


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


    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null

    }
}