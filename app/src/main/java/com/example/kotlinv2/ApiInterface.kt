package com.example.kotlinv2

import com.example.kotlin.model.Movie
import com.example.kotlin.model.MovieResponse
import com.example.kotlinv2.model.MovieDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Call<MovieResponse>

    @GET("trending/movie/{time_window}")
    fun getTrendingMovies(
        @Path("time_window") time_window: String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        ): Call<MovieResponse>

    @GET("movie/{movie_id}")
    fun getMovieBasedOnId(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
    ): Call<MovieDetail>

    @GET("movie/{movie_id}/similar")
    fun getSimilarMovies(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Call<MovieResponse>

    @GET("search/movie")
    fun getMoviesBySearch(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("query") query: String,
    ): Call<MovieResponse>
}