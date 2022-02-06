package com.phoenixdevelopers.watflix.ui.fragments.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.phoenixdevelopers.watflix.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, bundle: Bundle?
    ): View? {

        _binding = FragmentDetailBinding
            .inflate(inflater, container, false);

        return binding.root

    }

    override fun onViewCreated(view: View, bundle: Bundle?) {
        super.onViewCreated(view, bundle)


    }


    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null

    }
}