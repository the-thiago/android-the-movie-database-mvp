package com.br.thiago.themoviedatabaseapp.ui.search

import android.net.ConnectivityManager
import com.br.thiago.themoviedatabaseapp.model.Movie

class SearchContract {

    interface View {
        fun showLoadingScreen()
        fun hideLoadingScreen()
        fun showSearchedMovies(movies: List<Movie>)
        fun showEmptySearch()
        fun showNoInternetConnectionWarning()
    }

    interface Presenter {
        fun searchMovies(query: String, connectivityManager: ConnectivityManager)
        fun destroyView()
    }

}