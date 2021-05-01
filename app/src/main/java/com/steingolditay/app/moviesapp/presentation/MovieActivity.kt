package com.steingolditay.app.moviesapp.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import com.steingolditay.app.moviesapp.R
import com.steingolditay.app.moviesapp.databinding.ActivityMovieBinding
import com.steingolditay.app.moviesapp.models.Movie
import com.steingolditay.app.moviesapp.models.MovieDetails
import com.steingolditay.app.moviesapp.utils.Constants
import com.steingolditay.app.moviesapp.viewmodels.MovieViewModel
import com.steingolditay.app.moviesapp.viewmodels.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieActivity: AppCompatActivity() {

    private lateinit var binding: ActivityMovieBinding
    private lateinit var viewModel: MovieViewModel

    private lateinit var genresMap: Map<Int, String>
    private lateinit var movie: MovieDetails
    private lateinit var movieId: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        getMovieId()
        showProgressBar()
        initViewModel()
    }

    private fun initViewModel(){
        viewModel = ViewModelProvider(this).get(MovieViewModel::class.java)

        viewModel.movieDetails.observe(this, { result ->
            hideProgressBar()
            if (result != null){
                movie = result
                updateUi()
            }
        })

        viewModel.getMovieDetails(movieId)
    }


    private fun getMovieId(){
        val bundle = intent.extras
        if (bundle != null && bundle.containsKey(Constants.movieId)){
            movieId = bundle.get(Constants.movieId) as String
        }
    }

    private fun updateUi(){
        Picasso.get().load(Constants.imageBaseUrl + movie.backdrop_path).placeholder(R.drawable.movie).into(binding.image)
        binding.name.text = movie.title
        binding.overview.text = movie.overview
        binding.popularity.text = movie.popularity.toString()
        binding.releaseDate.text = movie.release_date
        binding.language.text = movie.original_language
        binding.genres.text = movie.genres.joinToString { it.name }
    }

    private fun showProgressBar(){binding.progressBar.visibility = View.VISIBLE}

    private fun hideProgressBar(){binding.progressBar.visibility = View.GONE}

}