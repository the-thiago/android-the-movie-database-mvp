package com.br.thiago.themoviedatabaseapp.ui.favorites

import android.net.ConnectivityManager
import com.br.thiago.themoviedatabaseapp.model.Movie
import com.br.thiago.themoviedatabaseapp.util.hasInternetConnection
import com.parse.ParseQuery

class FavoritesPresenter(private var view: FavoritesContract.View?) : FavoritesContract.Presenter {

    override fun getAllMovies(connectivityManager: ConnectivityManager) {
        if (hasInternetConnection(connectivityManager)) {
            safeGetAllMovies()
        } else {
            view?.showNoInternetConnectionWarning()
        }
    }

    private fun safeGetAllMovies() {
        val query = ParseQuery.getQuery<Movie>("Movie")
        query.findInBackground { moviesFromParse, exception ->
            if (exception == null) {
                if (moviesFromParse.isEmpty()) {
                    view?.showNoFavoriteMovieText()
                } else {
                    view?.showMovieList(moviesFromParse)
                }
            } else {
                view?.showErrorMessage()
            }
        }
    }

    override fun destroyView() {
        view = null
    }

}