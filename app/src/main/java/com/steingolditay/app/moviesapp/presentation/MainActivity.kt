package com.steingolditay.app.moviesapp.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.steingolditay.app.moviesapp.R
import com.steingolditay.app.moviesapp.databinding.ActivityMainBinding
import com.steingolditay.app.moviesapp.presentation.fragments.FragmentFavorites
import com.steingolditay.app.moviesapp.presentation.fragments.FragmentInTheaters
import com.steingolditay.app.moviesapp.presentation.fragments.FragmentPopular
import com.steingolditay.app.moviesapp.utils.Constants
import com.steingolditay.app.moviesapp.viewmodels.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: SharedViewModel

    private val fragmentPopular = FragmentPopular()
    private val fragmentInTheaters = FragmentInTheaters()
    private val fragmentFavorites = FragmentFavorites()

    private var loadedConfiguration = false
    private var loadedGenres = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        showProgressBar()
        initViewModel()
        initNavigationBar()

    }


    private fun initViewModel(){
        viewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
        viewModel.movieGenres.observe(this, { result ->
            if (result != null){
                if (!loadedGenres){
                    loadedGenres = true
                    if (loadedConfiguration) {
                        hideProgressBar()
                        setFragment(fragmentPopular)
                    }
                }
            }

        })
        viewModel.configurations.observe(this, { result ->
            if (result != null){
                Constants.imageBaseUrl = "${result.images.secure_base_url}w500"

                if (!loadedConfiguration){
                    loadedConfiguration = true
                    if (loadedGenres){
                        hideProgressBar()
                        setFragment(fragmentPopular)
                    }
                }
            }
        })

        viewModel.getMovieGenres()
        viewModel.getConfigurations()
    }

    private fun initNavigationBar(){
        binding.navigation.setOnNavigationItemSelectedListener{ item ->
            when (item.itemId) {

                R.id.popular -> setFragment(fragmentPopular)

                R.id.inTheaters -> setFragment(fragmentInTheaters)

                R.id.favorites -> setFragment(fragmentFavorites)
            }
            true
        }
    }

    private fun setFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentsContainer, fragment)
                commit()
        }
    }

    private fun showProgressBar(){binding.progressBar.visibility = View.VISIBLE}

    private fun hideProgressBar(){binding.progressBar.visibility = View.GONE}

}

