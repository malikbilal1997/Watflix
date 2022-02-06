package com.phoenixdevelopers.watflix.ui.fragments.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.phoenixdevelopers.watflix.R
import com.phoenixdevelopers.watflix.databinding.FragmentSplashBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        bundle: Bundle?
    ): View {

        _binding = FragmentSplashBinding
            .inflate(inflater, container, false);

        return binding.root

    }

    override fun onResume() {
        super.onResume()

        gotoHomeScreen()

    }

    private fun gotoHomeScreen() {

        lifecycleScope.launch {

            withContext(Dispatchers.IO) {
                delay(2000)
            }

            withContext(Dispatchers.Main) {
                navigateToHome()
            }
        }
    }

    private fun navigateToHome() {

        findNavController().navigate(R.id.action_splashFragment_to_homeFragment)

    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null

    }
}