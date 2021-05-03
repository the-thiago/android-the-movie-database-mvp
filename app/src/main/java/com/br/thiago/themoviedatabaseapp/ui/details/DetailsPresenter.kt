package com.br.thiago.themoviedatabaseapp.ui.details

import android.net.ConnectivityManager
import com.br.thiago.themoviedatabaseapp.api.MovieService
import com.br.thiago.themoviedatabaseapp.model.Movie
import com.br.thiago.themoviedatabaseapp.util.Constants.Companion.ID_KEY
import com.br.thiago.themoviedatabaseapp.util.hasInternetConnection
import com.br.thiago.themoviedatabaseapp.util.toMovie
import com.parse.ParseACL
import com.parse.ParseQuery
import com.parse.ParseUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class DetailsPresenter(
    private var view: DetailsContract.View?,
    private val movieService: MovieService
) : DetailsContract.Presenter {

    override fun getMovieDetails(
        movieId: Int,
        isFromDatabase: Boolean,
        connectivityManager: ConnectivityManager
    ) {
        if (hasInternetConnection(connectivityManager)) {
            safeGetMovieDetails(movieId)
        } else {
            view?.showNoInternetConnectionWarning()
        }
    }

    private fun safeGetMovieDetails(movieId: Int) {
        var isFavoriteMovie = false
        val query = ParseQuery.getQuery<Movie>("Movie")
        query.whereEqualTo(ID_KEY, movieId)
        query.getFirstInBackground { movie, exception ->
            if (exception == null) {
                isFavoriteMovie = true
                view?.setMovie(movie)
                setupLayout(movie)
                view?.setFabAsFavoriteMovie()
            }
        }
        if (!isFavoriteMovie) {
            var movie: Movie
            CoroutineScope(Dispatchers.IO).launch {
                val movieDetailsResponse = movieService.getMovieDetails(movieId).body()
                movieDetailsResponse?.toMovie()?.let {
                    movie = it
                    withContext(Dispatchers.Main) {
                        view?.setMovie(movie)
                        view?.setupLayout(movie)
                        view?.hideLoadingScreen()
                    }
                }
            }
        }
    }

    override fun addOrRemoveFromParse(
        movie: Movie,
        isFavoriteMovie: Boolean,
        connectivityManager: ConnectivityManager
    ) {
        if (hasInternetConnection(connectivityManager)) {
            safeAddOrRemoveFromParse(isFavoriteMovie, movie)
        } else {
            view?.showNoInternetConnectionWarning()
        }
        view?.hideLoadingScreen()
    }

    private fun safeAddOrRemoveFromParse(isFavoriteMovie: Boolean, movie: Movie) {
        if (isFavoriteMovie) {
            val query = ParseQuery.getQuery<Movie>("Movie")
            query.whereEqualTo(ID_KEY, movie.movieId)
            query.getFirstInBackground { movieFromParse, exception ->
                if (exception == null) {
                    movieFromParse.deleteInBackground()
                } else {
                    view?.showErrorMessage()
                }
            }
            view?.setFabAsNotFavoriteMovie()
        } else {
            movie.acl = ParseACL(ParseUser.getCurrentUser())
            movie.saveInBackground()
            view?.setFabAsFavoriteMovie()
        }
    }

    private fun setupLayout(movie: Movie?) {
        movie?.let {
            view?.setupLayout(movie)
            view?.hideLoadingScreen()
        }
    }

    override fun destroyView() {
        view = null
    }

}