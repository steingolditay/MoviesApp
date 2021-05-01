package com.steingolditay.app.moviesapp.presentation.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import com.steingolditay.app.moviesapp.R
import com.steingolditay.app.moviesapp.databinding.ActivityMovieBinding
import com.steingolditay.app.moviesapp.models.MovieDetails
import com.steingolditay.app.moviesapp.utils.Constants
import com.steingolditay.app.moviesapp.viewmodels.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieActivity: AppCompatActivity() {

    private lateinit var binding: ActivityMovieBinding
    private lateinit var viewModel: MovieViewModel

    private lateinit var movie: MovieDetails
    private lateinit var movieId: String
    private  var isFavorite = false


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

        viewModel.favorites.observe(this, { favorites ->
            if (favorites != null){
                isFavorite = favorites.containsKey(movieId)
            }
        })

        viewModel.getMovieDetails(movieId)
        viewModel.getFavorites()
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

        if (isFavorite){
            binding.favorite.setImageResource (R.drawable.favorite_filled)
        }
        else {
            binding.favorite.setImageResource (R.drawable.favorite)
        }


        binding.favorite.setOnClickListener{
            if (isFavorite){
                binding.favorite.setImageResource (R.drawable.favorite)
                removeFromFavorites()
                isFavorite = false
            }
            else {
                binding.favorite.setImageResource (R.drawable.favorite_filled)
                addToFavorites()
                isFavorite = true

            }
        }
    }



    private fun addToFavorites(){viewModel.addFavorite(movie)}

    private fun removeFromFavorites(){viewModel.removeFavorite(movie)}

    private fun showProgressBar(){binding.progressBar.visibility = View.VISIBLE}

    private fun hideProgressBar(){binding.progressBar.visibility = View.GONE}

}