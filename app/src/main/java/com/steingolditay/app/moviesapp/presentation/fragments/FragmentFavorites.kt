package com.steingolditay.app.moviesapp.presentation.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.steingolditay.app.moviesapp.databinding.FragmentSharedViewBinding
import com.steingolditay.app.moviesapp.models.Movie
import com.steingolditay.app.moviesapp.presentation.activities.MovieActivity
import com.steingolditay.app.moviesapp.presentation.fragments.adapters.MoviesAdapter
import com.steingolditay.app.moviesapp.utils.Constants
import com.steingolditay.app.moviesapp.viewmodels.SharedViewModel

class FragmentFavorites : Fragment(), MoviesAdapter.OnItemClickListener {

    private var _binding: FragmentSharedViewBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SharedViewModel by activityViewModels()
    private lateinit var adapter: MoviesAdapter

    private lateinit var movieList: List<Movie>

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

        initViewModel()
    }

    override fun onStart() {
        super.onStart()
        viewModel.getFavorites()

    }

    private fun initViewModel() {
        viewModel.favorites.observe(viewLifecycleOwner, { favorites ->
            if (favorites != null) {
                movieList = favorites.values.toList()
                updateMoviesAdapter()
            }
        })

        viewModel.getFavorites()

    }

    private fun updateMoviesAdapter() {
        adapter = MoviesAdapter(movieList, this)
        binding.recyclerView.layoutManager = GridLayoutManager(context, 2)
        binding.recyclerView.adapter = adapter
    }


    override fun onItemClick(movie: Movie) {
        val intent = Intent(requireContext(), MovieActivity::class.java)
        intent.putExtra(Constants.movieId, movie.id.toString())
        startActivity(intent)
    }
}