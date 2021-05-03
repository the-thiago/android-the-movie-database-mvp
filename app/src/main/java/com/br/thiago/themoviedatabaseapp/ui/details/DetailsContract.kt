package com.br.thiago.themoviedatabaseapp.ui.details

import android.net.ConnectivityManager
import com.br.thiago.themoviedatabaseapp.model.Movie

interface DetailsContract {

    interface View {
        fun setupLayout(movie: Movie)
        fun showLoadingScreen()
        fun hideLoadingScreen()
        fun setFabAsFavoriteMovie()
        fun setFabAsNotFavoriteMovie()
        fun setMovie(movie: Movie)
        fun showNoInternetConnectionWarning()
        fun showErrorMessage()
    }

    interface Presenter {
        fun getMovieDetails(
            movieId: Int,
            isFromDatabase: Boolean,
            connectivityManager: ConnectivityManager
        )

        fun addOrRemoveFromParse(
            movie: Movie,
            isFavoriteMovie: Boolean,
            connectivityManager: ConnectivityManager
        )

        fun destroyView()
    }

}