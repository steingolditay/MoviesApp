package com.steingolditay.app.moviesapp.models



class Movie(

    val id: Int,
    val genre_ids: List<Int>,
    val backdrop_path: String,
    val original_language: String,
    val title: String,
    val overview: String,
    val popularity: Double,
    val release_date: String,


    )