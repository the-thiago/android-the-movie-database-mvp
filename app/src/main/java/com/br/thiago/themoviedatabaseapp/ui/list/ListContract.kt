package com.br.thiago.themoviedatabaseapp.ui.list

import com.br.thiago.themoviedatabaseapp.model.Movie

interface ListContract {

    interface View {
        fun showMovieList(movies: List<Movie>)
        fun showLoadingScreen()
        fun hideLoadingScreen()
    }

    interface Presenter {
        fun getMoviesFromApi()
        fun destroyView()
    }

}