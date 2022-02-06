package com.phoenixdevelopers.watflix.network

import com.phoenixdevelopers.watflix.model.MovieDetail
import com.phoenixdevelopers.watflix.model.Movies
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("get_all_photos")
    suspend fun getAllMovies():Movies

    @GET("get_single_photo_detail")
    suspend fun getMovieDetail(

        @Query("photo_id") movieId: String

    ):MovieDetail
}