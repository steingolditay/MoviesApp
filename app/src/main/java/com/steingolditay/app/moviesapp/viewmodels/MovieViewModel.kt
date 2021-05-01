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
class MovieViewModel

@Inject constructor(private val repository: Repository): ViewModel(){

    private val _movieDetails = MutableLiveData<MovieDetails?>()
    val movieDetails: LiveData<MovieDetails?> = _movieDetails

    private val _favorites = MutableLiveData<HashMap<String, Movie>?>()
    val favorites: LiveData<HashMap<String, Movie>?> = _favorites


    fun getMovieDetails(movieId: String){
        viewModelScope.launch(Dispatchers.IO) {
            _movieDetails.postValue(repository.getMovieDetails(movieId))

        }
    }

    fun getFavorites(){
        val structure = MapStructure.create(HashMap::class.java, String::class.java, Movie::class.java)
        val favorites: HashMap<String, Movie>? = PowerPreference.getDefaultFile().getMap(Constants.favorites, structure)
        _favorites.postValue(favorites)
    }

    fun addFavorite(movieToConvert: MovieDetails){
        val movie = convertMovie(movieToConvert)
        val structure = MapStructure.create(HashMap::class.java, String::class.java, Movie::class.java)
        val favorites: HashMap<String, Movie>? = PowerPreference.getDefaultFile().getMap(Constants.favorites, structure)
        if (favorites != null){
            favorites[movie.id.toString()] = movie
            PowerPreference.getDefaultFile().putMap(Constants.favorites, favorites)
            _favorites.postValue(favorites)

        }
        else {
            val map = HashMap<String, Movie>()
            map[movie.id.toString()] = movie
            PowerPreference.getDefaultFile().putMap(Constants.favorites, map)
        }
    }

    fun removeFavorite(movieToConvert: MovieDetails){
        val movie = convertMovie(movieToConvert)

        val structure = MapStructure.create(HashMap::class.java, String::class.java, Movie::class.java)
        val favorites: HashMap<String, Movie>? = PowerPreference.getDefaultFile().getMap(Constants.favorites, structure)
        if (!favorites.isNullOrEmpty() && favorites.containsKey(movie.id.toString()) ){
            favorites.remove(movie.id.toString())
            PowerPreference.getDefaultFile().putMap(Constants.favorites, favorites)
            _favorites.postValue(favorites)

        }
    }

    private fun convertMovie(movieDetails: MovieDetails) : Movie {
        return Movie(movieDetails.id,
                listOf(),
                movieDetails.backdrop_path,
                movieDetails.original_language,
                movieDetails.title,
                movieDetails.overview,
                movieDetails.popularity,
                movieDetails.release_date)
    }


}