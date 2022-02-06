package com.phoenixdevelopers.watflix.di

import com.phoenixdevelopers.watflix.network.ApiInterface
import com.phoenixdevelopers.watflix.repos.MoviesRepo
import com.phoenixdevelopers.watflix.repos.MoviesRepoImpl
import com.phoenixdevelopers.watflix.utils.API_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppComponents {


    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Singleton
    @Provides
    fun providesMoviesRepo(apiInterface: ApiInterface):
            MoviesRepo = MoviesRepoImpl(apiInterface)

    @Singleton
    @Provides
    fun providesApiInterface(retrofit: Retrofit): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }

}