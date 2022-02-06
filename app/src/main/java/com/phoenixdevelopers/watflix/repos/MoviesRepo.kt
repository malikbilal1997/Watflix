package com.phoenixdevelopers.watflix.repos

import com.phoenixdevelopers.watflix.model.Movie
import kotlinx.coroutines.flow.Flow

interface MoviesRepo {

    fun getAllMovies(): Flow<List<Movie>>

    suspend fun getMovieDetail(movieId: String): Movie

    fun getSimilarMovies(movieId: String): Flow<List<Movie>>
}