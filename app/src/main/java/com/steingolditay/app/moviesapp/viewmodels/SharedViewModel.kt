package com.steingolditay.app.moviesapp.viewmodels

import androidx.lifecycle.*
import com.steingolditay.app.moviesapp.models.ConfigurationsJsonResponse
import com.steingolditay.app.moviesapp.models.GenresJsonResponse
import com.steingolditay.app.moviesapp.models.Movie
import com.steingolditay.app.moviesapp.models.MoviesJsonResponse
import com.steingolditay.app.moviesapp.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel

@Inject constructor(private val repository: Repository): ViewModel(){


    private val _movieGenres = MutableLiveData<GenresJsonResponse?>()
    val movieGenres: LiveData<GenresJsonResponse?> = _movieGenres

    private val _configurations = MutableLiveData<ConfigurationsJsonResponse?>()
    val configurations: LiveData<ConfigurationsJsonResponse?> = _configurations

    private val _popularMovies = MutableLiveData<MoviesJsonResponse?>()
    val popularMovies: LiveData<MoviesJsonResponse?> = _popularMovies

    private val _moviesInTheaters = MutableLiveData<MoviesJsonResponse?>()
    val moviesInTheaters: LiveData<MoviesJsonResponse?> = _moviesInTheaters

    private val _movieDetails = MutableLiveData<Movie?>()
    val movieDetails: LiveData<Movie?> = _movieDetails


    fun getMovieGenres(){
        viewModelScope.launch(Dispatchers.IO) {
            _movieGenres.postValue(repository.getMovieGenres())
        }
    }

    fun getConfigurations(){
        viewModelScope.launch(Dispatchers.IO){
            _configurations.postValue(repository.getConfigurations())
        }
    }

    fun getPopularMovies(pageNumber: String){
        viewModelScope.launch(Dispatchers.IO) {
            val existingValue = _popularMovies.value
            val updatedValue = repository.getPopularMovies(pageNumber)
            if (existingValue != null && updatedValue != null) {
                val mergedResult = existingValue.results + updatedValue.results
                _popularMovies.postValue(MoviesJsonResponse(mergedResult, existingValue.total_pages))
            }
            else {
                _popularMovies.postValue(updatedValue)
            }
        }
    }

    fun getMoviesInTheaters(pageNumber: String){
        viewModelScope.launch(Dispatchers.IO){
            val existingValue = _moviesInTheaters.value
            val updatedValue = repository.getMoviesInTheaters(pageNumber)
            if (existingValue != null && updatedValue != null){
                val mergedResult = existingValue.results + updatedValue.results
                _moviesInTheaters.postValue(MoviesJsonResponse(mergedResult, existingValue.total_pages))
            }
            else {
                _moviesInTheaters.postValue(updatedValue)
            }
        }
    }

    fun getMovieDetails(movieId: String){
        viewModelScope.launch(Dispatchers.IO) {
            _movieDetails.postValue(repository.getMovieDetails(movieId))

        }
    }

}