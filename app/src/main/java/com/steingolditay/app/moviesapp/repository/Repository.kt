package com.steingolditay.app.moviesapp.repository

import com.steingolditay.app.moviesapp.di.RetrofitInterface
import com.steingolditay.app.moviesapp.models.*
import com.steingolditay.app.moviesapp.models.jsonResponses.ConfigurationsJsonResponse
import com.steingolditay.app.moviesapp.models.jsonResponses.MoviesJsonResponse
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.lang.Exception
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
class Repository
@Inject constructor(private val retrofit: RetrofitInterface) {

    suspend fun getConfigurations(): ConfigurationsJsonResponse? {
        return try {
            retrofit.getConfiguration()
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getPopularMovies(pageNumber: String): MoviesJsonResponse? {
        return try {
            retrofit.getPopularMovies(pageNumber)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getMoviesInTheaters(pageNumber: String): MoviesJsonResponse? {
        return try {
            retrofit.getMoviesInTheaters(pageNumber)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getMovieDetails(movieId: String): MovieDetails? {
        return try {
            retrofit.getMovieDetails(movieId)
        } catch (e: Exception) {
            null
        }
    }


}