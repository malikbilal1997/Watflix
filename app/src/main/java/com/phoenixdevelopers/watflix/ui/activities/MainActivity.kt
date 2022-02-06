package com.phoenixdevelopers.watflix.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.phoenixdevelopers.watflix.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding
            .inflate(layoutInflater)

        setContentView(binding.root)
    }
}