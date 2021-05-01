package com.steingolditay.app.moviesapp.presentation.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.steingolditay.app.moviesapp.R
import com.steingolditay.app.moviesapp.databinding.ActivityMainBinding
import com.steingolditay.app.moviesapp.presentation.fragments.FragmentFavorites
import com.steingolditay.app.moviesapp.presentation.fragments.FragmentInTheaters
import com.steingolditay.app.moviesapp.presentation.fragments.FragmentPopular
import com.steingolditay.app.moviesapp.utils.Constants
import com.steingolditay.app.moviesapp.utils.NetworkConnectionMonitor
import com.steingolditay.app.moviesapp.viewmodels.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: SharedViewModel

    private val fragmentPopular = FragmentPopular()
    private val fragmentInTheaters = FragmentInTheaters()
    private val fragmentFavorites = FragmentFavorites()

    private var isConnected = false
    private var dataLoaded = false

    @Inject
    lateinit var networkConnectionMonitor: NetworkConnectionMonitor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initNetworkConnectionMonitor()
        showProgressBar()
        initViewModel()
        initNavigationBar()

    }

    private fun initNetworkConnectionMonitor() {
        networkConnectionMonitor.registerNetworkCallback()
        networkConnectionMonitor.liveData.observe(this, {
            isConnected = it
            if (isConnected && !dataLoaded) {
                initViewModel()
            }
        })
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
        viewModel.getConfigurations()

        viewModel.configurations.observe(this, { result ->
            if (result != null) {
                Constants.imageBaseUrl = "${result.images.secure_base_url}w500"
                hideProgressBar()
                dataLoaded = true
                setFragment(fragmentPopular)
            } else {
                toastConnectionError()
            }
        })


    }

    private fun initNavigationBar() {
        binding.navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {

                R.id.popular -> setFragment(fragmentPopular)

                R.id.inTheaters -> setFragment(fragmentInTheaters)

                R.id.favorites -> setFragment(fragmentFavorites)
            }
            true
        }
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentsContainer, fragment)
            commit()
        }
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }

    private fun toastConnectionError() {
        val msg = if (isConnected) getString(R.string.connectionError) else getString(R.string.notConnected)
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

}

