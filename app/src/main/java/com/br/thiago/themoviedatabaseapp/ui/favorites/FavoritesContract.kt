package com.br.thiago.themoviedatabaseapp.ui.favorites

import android.net.ConnectivityManager
import com.br.thiago.themoviedatabaseapp.model.Movie

interface FavoritesContract {

    interface View {
        fun showMovieList(movies: List<Movie>)
        fun showNoFavoriteMovieText()
        fun showNoInternetConnectionWarning()
    }

    interface Presenter {
        fun getAllMovies(connectivityManager: ConnectivityManager)
        fun destroyView()
    }

}