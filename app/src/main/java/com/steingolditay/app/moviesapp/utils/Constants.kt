package com.steingolditay.app.moviesapp.utils

object Constants {
    var apiBaseUrl = "https://api.themoviedb.org/3/"
    var imageBaseUrl = ""

    private const val apiKey = "2c46288716a18fb7aadcc2a801f3fc6b"
    const val queryGenres = "genre/movie/list?api_key=$apiKey"
    const val queryConfiguration = "configuration?api_key=$apiKey"
    const val queryPopularMovies = "movie/popular?api_key=$apiKey"
    const val queryInTheatersMovies = "movie/now_playing?api_key=$apiKey"
    const val queryMovieDetails = "movie/{movie_id}?api_key=$apiKey"



    const val movieId = "movieId"

}