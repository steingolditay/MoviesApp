package com.steingolditay.app.moviesapp.models.jsonResponses

import com.steingolditay.app.moviesapp.models.Movie

class MoviesJsonResponse(
        val results: List<Movie>,
        val total_pages: Int
)

