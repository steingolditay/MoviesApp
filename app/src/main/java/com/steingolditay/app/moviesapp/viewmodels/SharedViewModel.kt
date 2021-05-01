package com.steingolditay.app.moviesapp.viewmodels

import androidx.lifecycle.*
import com.preference.MapStructure
import com.preference.PowerPreference
import com.steingolditay.app.moviesapp.models.*
import com.steingolditay.app.moviesapp.repository.Repository
import com.steingolditay.app.moviesapp.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel

@Inject constructor(private val repository: Repository): ViewModel(){

    
    private val _configurations = MutableLiveData<ConfigurationsJsonResponse?>()
    val configurations: LiveData<ConfigurationsJsonResponse?> = _configurations

    private val _popularMovies = MutableLiveData<MoviesJsonResponse?>()
    val popularMovies: LiveData<MoviesJsonResponse?> = _popularMovies

    private val _moviesInTheaters = MutableLiveData<MoviesJsonResponse?>()
    val moviesInTheaters: LiveData<MoviesJsonResponse?> = _moviesInTheaters

    private val _favorites = MutableLiveData<HashMap<String, Movie>?>()
    val favorites: LiveData<HashMap<String, Movie>?> = _favorites


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

    fun getFavorites(){
        val structure = MapStructure.create(HashMap::class.java, String::class.java, Movie::class.java)
        val favorites: HashMap<String, Movie>? = PowerPreference.getDefaultFile().getMap(Constants.favorites, structure)
        _favorites.postValue(favorites)
    }

}