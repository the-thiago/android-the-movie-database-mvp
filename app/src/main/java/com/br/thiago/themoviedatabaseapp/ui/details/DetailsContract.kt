package com.br.thiago.themoviedatabaseapp.ui.details

import android.content.Context
import com.br.thiago.themoviedatabaseapp.model.Movie

interface DetailsContract {

    interface View {
        fun setupLayout(movie: Movie)
        fun showLoadingScreen()
        fun hideLoadingScreen()
        fun setFabAsFavoriteMovie()
        fun setFabAsNotFavoriteMovie()
    }

    interface Presenter {
        fun getMovieDetails(movieId: Int, isFromDatabase: Boolean, context: Context)
        fun addOrRemoveFromDatabase(movie: Movie, isFavoriteMovie: Boolean, context: Context)
        fun isFavoriteMovie(isFromDatabase: Boolean, context: Context): Boolean
    }

}