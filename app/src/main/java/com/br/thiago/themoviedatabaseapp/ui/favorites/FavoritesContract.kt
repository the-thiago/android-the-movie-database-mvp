package com.br.thiago.themoviedatabaseapp.ui.favorites

import android.content.Context
import com.br.thiago.themoviedatabaseapp.model.Movie

interface FavoritesContract {

    interface View {
        fun showMovieList(movies: List<Movie>)
        fun showNoFavoriteMovieText()
    }

    interface Presenter {
        fun getAllMovies()
        fun destroyView()
    }

}