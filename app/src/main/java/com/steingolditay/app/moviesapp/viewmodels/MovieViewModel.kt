package com.steingolditay.app.moviesapp.viewmodels

import androidx.lifecycle.*
import com.steingolditay.app.moviesapp.models.*
import com.steingolditay.app.moviesapp.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel

@Inject constructor(private val repository: Repository): ViewModel(){


    private val _movieGenres = MutableLiveData<List<Genre>>()
    val movieGenres: LiveData<List<Genre>> = _movieGenres

    private val _movieDetails = MutableLiveData<Movie?>()
    val movieDetails: LiveData<Movie?> = _movieDetails


    fun getMovieGenres(){
            _movieGenres.postValue(repository.movieGenres.value!!)
    }

    fun getMovieDetails(movieId: String){
        viewModelScope.launch(Dispatchers.IO) {
            _movieDetails.postValue(repository.getMovieDetails(movieId))

        }
    }

}