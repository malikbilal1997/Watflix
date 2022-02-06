package com.phoenixdevelopers.watflix.model

import com.google.gson.annotations.SerializedName

data class MovieDetail(

    @SerializedName("data")
    val movie:Movie,

    @SerializedName("status")
    val status:Int,

    @SerializedName("message")
    val message:String

)
