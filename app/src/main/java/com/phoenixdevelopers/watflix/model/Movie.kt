package com.phoenixdevelopers.watflix.model

import com.google.gson.annotations.SerializedName

data class Movie(

    @SerializedName("id")
    val id : String,

    @SerializedName("title")
    val title:String,

    @SerializedName("year")
    val year:String,

    @SerializedName("genres")
    val genre:String,

    @SerializedName("rating")
    val rating:String,

    @SerializedName("language")
    val language:String,

    @SerializedName("summary")
    val summary:String,

    @SerializedName("create_at")
    val createTime:String,

    @SerializedName("title_long")
    val titleLong:String,

    @SerializedName("cover_image")
    val coverImage:String,

    @SerializedName("background_image")
    val backgroundImage:String
)
