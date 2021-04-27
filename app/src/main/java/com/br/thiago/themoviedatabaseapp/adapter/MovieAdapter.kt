package com.br.thiago.themoviedatabaseapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.br.thiago.themoviedatabaseapp.R
import com.br.thiago.themoviedatabaseapp.model.Movie
import com.bumptech.glide.Glide

class MovieAdapter(private val callback: (Movie) -> Unit) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private var movies = emptyList<Movie>()

    fun setItems(list: List<Movie>) {
        movies = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount() = movies.size


    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivImageMovie: ImageView = itemView.findViewById(R.id.ivImageMovie)
        private val tvMovieTitle: TextView = itemView.findViewById(R.id.tvMovieTitle)

        fun bind(movie: Movie) {
            tvMovieTitle.text = movie.title

            Glide
                .with(itemView)
                .load("url")
                .placeholder(R.drawable.ic_movie_image_placeholder)
                .centerCrop()
                .into(ivImageMovie)
        }
    }
}