package com.steingolditay.app.moviesapp.presentation.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.steingolditay.app.moviesapp.databinding.FragmentSharedViewBinding
import com.steingolditay.app.moviesapp.models.Movie
import com.steingolditay.app.moviesapp.presentation.MovieActivity
import com.steingolditay.app.moviesapp.presentation.fragments.adapters.MoviesAdapter
import com.steingolditay.app.moviesapp.utils.Constants
import com.steingolditay.app.moviesapp.viewmodels.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentInTheaters: Fragment(), MoviesAdapter.OnItemClickListener {

    private var _binding: FragmentSharedViewBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SharedViewModel by activityViewModels()
    private lateinit var adapter: MoviesAdapter

    private var currentPageNumber = 1
    private var totalPages = 1
    private var movieList = ArrayList<Movie>()

    private var firstTime = true

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSharedViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.seeMore.setOnClickListener {
            loadPage()
        }

        initViewModel()
        addRecyclerViewScrollListener()

    }

    private fun initViewModel(){
        viewModel.moviesInTheaters.observe(viewLifecycleOwner, { result ->
            hideProgressBar()
            if (result != null){
                movieList = ArrayList(result.results)
                totalPages = result.total_pages
                updateMoviesAdapter()
            }
        })

        if (firstTime){
            firstTime = false
            viewModel.getMoviesInTheaters(currentPageNumber.toString())
            currentPageNumber += 1
        }

    }

    private fun updateMoviesAdapter(){
        adapter = MoviesAdapter(movieList.toList(), this)
        binding.recyclerView.layoutManager = GridLayoutManager(context, 2)
        binding.recyclerView.adapter = adapter

        scrollDownRecyclerView()
    }

    private fun addRecyclerViewScrollListener(){
        binding.recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val position = (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                if (position + 1 == movieList.size) {
                    showSeeMoreButton()
                }
                else {
                    hideSeeMoreButton()
                }
            }
        })
    }

    private fun loadPage(){
        if (currentPageNumber < totalPages && movieList.isNotEmpty()){
            showProgressBar()
            viewModel.getMoviesInTheaters(currentPageNumber.toString())
            currentPageNumber += 1
        }
    }

    override fun onItemClick(movie: Movie) {
        val intent = Intent(requireContext(), MovieActivity::class.java)
        intent.putExtra(Constants.movieId, movie.id.toString())
        startActivity(intent)
    }

    private fun scrollDownRecyclerView(){binding.recyclerView.smoothScrollToPosition((currentPageNumber -2) * 20 + 8)}

    private fun showProgressBar(){binding.progressBar.visibility = View.VISIBLE}

    private fun hideProgressBar(){binding.progressBar.visibility = View.GONE}

    private fun showSeeMoreButton(){binding.seeMore.visibility = View.VISIBLE}

    private fun hideSeeMoreButton(){binding.seeMore.visibility = View.GONE}

}