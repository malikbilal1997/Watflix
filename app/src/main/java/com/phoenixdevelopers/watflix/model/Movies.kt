package com.phoenixdevelopers.watflix.model

import com.google.gson.annotations.SerializedName

data class Movies(

    @SerializedName("data")
    val movies: List<Movie>

)