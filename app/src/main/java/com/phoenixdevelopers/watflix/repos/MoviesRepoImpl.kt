package com.phoenixdevelopers.watflix.repos

import com.phoenixdevelopers.watflix.model.Movie
import com.phoenixdevelopers.watflix.network.ApiInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MoviesRepoImpl @Inject constructor(

    private val apiInterface: ApiInterface

) : MoviesRepo {

    override suspend fun getMovieDetail(movieId: String): Movie {

        return withContext(Dispatchers.IO) {

            apiInterface.getMovieDetail(movieId).movie
        }
    }

    override fun getSimilarMovies(movieId: String) = flow {

        val movies = apiInterface.getSimilarMovies(movieId)

        emit(movies.movies)

    }.flowOn(Dispatchers.IO)

    override fun getAllMovies(): Flow<List<Movie>> = flow {

        val movies = apiInterface.getAllMovies()

        emit(movies.movies)

    }.flowOn(Dispatchers.IO)
}