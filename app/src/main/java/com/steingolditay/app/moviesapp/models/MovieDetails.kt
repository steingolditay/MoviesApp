package com.steingolditay.app.moviesapp.models



class MovieDetails(

    val id: Int,
    val genres: List<Genre>,
    val backdrop_path: String,
    val original_language: String,
    val title: String,
    val overview: String,
    val popularity: Double,
    val release_date: String,

    )