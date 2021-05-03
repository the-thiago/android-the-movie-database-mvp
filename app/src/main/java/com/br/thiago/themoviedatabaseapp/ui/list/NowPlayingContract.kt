package com.br.thiago.themoviedatabaseapp.ui.list

import android.net.ConnectivityManager
import com.br.thiago.themoviedatabaseapp.model.Movie

interface NowPlayingContract {

    interface View {
        fun showMovieList(movies: List<Movie>)
        fun showLoadingScreen()
        fun hideLoadingScreen()
        fun showNoInternetConnectionWarning()
        fun showErrorMessage()
    }

    interface Presenter {
        fun getMoviesFromApi(connectivityManager: ConnectivityManager)
        fun destroyView()
    }

}