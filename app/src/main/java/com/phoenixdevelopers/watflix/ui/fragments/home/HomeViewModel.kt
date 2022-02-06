package com.phoenixdevelopers.watflix.ui.fragments.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phoenixdevelopers.watflix.model.Movie
import com.phoenixdevelopers.watflix.repos.MoviesRepo
import com.phoenixdevelopers.watflix.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(

    private val movieRepo: MoviesRepo

) : ViewModel() {

    private val _moviesResponse = MutableStateFlow<Response<List<Movie>>>(Response.Loading)

    val movieResponse = _moviesResponse.asStateFlow()

    init {
        getAllMovies()
    }

    fun getAllMovies() {

        viewModelScope.launch(Dispatchers.IO) {

            movieRepo.getAllMovies().collect { movies ->

                Timber.d("Movies List Size -> ${movies.size}")

                _moviesResponse.value = Response.Success(movies)

            }
        }
    }
}