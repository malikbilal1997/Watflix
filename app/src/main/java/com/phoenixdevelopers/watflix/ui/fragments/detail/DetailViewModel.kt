package com.phoenixdevelopers.watflix.ui.fragments.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phoenixdevelopers.watflix.model.Movie
import com.phoenixdevelopers.watflix.repos.MoviesRepo
import com.phoenixdevelopers.watflix.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val moviesRepo: MoviesRepo,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    init {
        val movieId: String = savedStateHandle["movieId"] ?: ""
        getMovieData(movieId)
    }

    private val _movieDetails = MutableStateFlow<Response<Movie>>(Response.Loading)
    val movieDetails = _movieDetails.asStateFlow()

    private val _similarMovies = MutableStateFlow<Response<List<Movie>>>(Response.Loading)
    val similarMovies = _similarMovies.asStateFlow()

    private fun getMovieData(movieId: String) {

        viewModelScope.launch {
            runCatching {
                moviesRepo.getMovieDetail(movieId)
            }.onFailure {
                _movieDetails.value = Response.Error(it)

            }.onSuccess {

                Timber.d("Movies Id -> ${it.id}")

                _movieDetails.value = Response.Success(it)
            }
            launch {

                try {

                    moviesRepo.getSimilarMovies(movieId).collect { movies ->

                        Timber.d("Movies List Size -> ${movies.size}")

                        _similarMovies.value = Response.Success(movies)
                    }
                } catch (exception: Exception) {

                    _similarMovies.value = Response.Error(exception)
                }

            }
        }
    }

}