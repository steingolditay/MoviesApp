package com.steingolditay.app.moviesapp.di

import com.steingolditay.app.moviesapp.models.*
import com.steingolditay.app.moviesapp.models.jsonResponses.ConfigurationsJsonResponse
import com.steingolditay.app.moviesapp.models.jsonResponses.MoviesJsonResponse
import com.steingolditay.app.moviesapp.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitInterface {


    @GET(Constants.queryConfiguration)
    suspend fun getConfiguration(): ConfigurationsJsonResponse?

    @GET(Constants.queryPopularMovies)
    suspend fun getPopularMovies(@Query("page") page: String): MoviesJsonResponse?

    @GET(Constants.queryInTheatersMovies)
    suspend fun getMoviesInTheaters(@Query("page") page: String): MoviesJsonResponse?

    @GET(Constants.queryMovieDetails)
    suspend fun getMovieDetails(@Path("movie_id") movie_id: String): MovieDetails?

}