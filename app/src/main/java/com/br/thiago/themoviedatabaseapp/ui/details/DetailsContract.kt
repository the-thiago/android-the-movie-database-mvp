package com.br.thiago.themoviedatabaseapp.ui.details

import com.br.thiago.themoviedatabaseapp.model.Movie

interface DetailsContract {

    interface View {
        fun setupLayout(movie: Movie)
        fun showLoadingScreen()
        fun hideLoadingScreen()
        fun setFabAsFavoriteMovie()
        fun setFabAsNotFavoriteMovie()
        fun setMovie(movie: Movie)
    }

    interface Presenter {
        fun getMovieDetails(movieId: Int, isFromDatabase: Boolean)
        fun addOrRemoveFromParse(movie: Movie, isFavoriteMovie: Boolean)
        fun destroyView()
    }

}