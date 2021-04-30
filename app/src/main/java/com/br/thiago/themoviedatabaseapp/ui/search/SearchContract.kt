package com.br.thiago.themoviedatabaseapp.ui.search

import com.br.thiago.themoviedatabaseapp.model.Movie

class SearchContract {

    interface View {
        fun showLoadingScreen()
        fun hideLoadingScreen()
        fun showSearchedMovies(movies: List<Movie>)
        fun showEmptySearch()
    }

    interface Presenter {
        fun searchMovies(query: String)
        fun destroyView()
    }

}