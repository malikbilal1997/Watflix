package com.phoenixdevelopers.watflix.repos

import com.phoenixdevelopers.watflix.model.Movie
import com.phoenixdevelopers.watflix.network.ApiInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MoviesRepoImpl @Inject constructor(

    private val apiInterface: ApiInterface

) : MoviesRepo {

    override suspend fun getMovieDetail(movieId: String): Movie {

        return apiInterface.getMovieDetail(movieId).movie

    }

    override fun getAllMovies(): Flow<List<Movie>> = flow {

        val movies = apiInterface.getAllMovies()

        emit(movies.movies)

    }
}