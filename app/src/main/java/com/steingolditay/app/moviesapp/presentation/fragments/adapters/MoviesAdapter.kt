package com.steingolditay.app.moviesapp.presentation.fragments.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.steingolditay.app.moviesapp.R
import com.steingolditay.app.moviesapp.models.Movie
import com.steingolditay.app.moviesapp.utils.Constants
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

class MoviesAdapter (private val movieList: List<Movie>,
                     private val listener: OnItemClickListener)
    :RecyclerView.Adapter<MoviesAdapter.ViewHolder>(){

    interface OnItemClickListener{
        fun onItemClick(movie: Movie)
    }

    inner class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        val name: TextView = itemView.findViewById(R.id.name)
        val popularity: TextView = itemView.findViewById(R.id.popularity)
        val image: ImageView = itemView.findViewById(R.id.image)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION){
                    listener.onItemClick(movieList[position])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_movie, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movieList[position]
        holder.name.text = movie.title
        holder.popularity.text = String.format(BigDecimal(movie.popularity).setScale(2, RoundingMode.HALF_DOWN).toString(), Locale.getDefault())

        val imageUrl = Constants.imageBaseUrl + movie.backdrop_path
        Picasso.get().load(imageUrl).placeholder(R.drawable.movie).into(holder.image)

    }

    override fun getItemCount(): Int {
        return movieList.size
    }

}