package com.br.thiago.themoviedatabaseapp.ui.details

import android.util.Log
import com.br.thiago.themoviedatabaseapp.api.MovieService
import com.br.thiago.themoviedatabaseapp.model.Movie
import com.br.thiago.themoviedatabaseapp.util.Constants.Companion.BACKDROP_PATH_KEY
import com.br.thiago.themoviedatabaseapp.util.Constants.Companion.ID_KEY
import com.br.thiago.themoviedatabaseapp.util.Constants.Companion.ORIGINAL_TITLE_KEY
import com.br.thiago.themoviedatabaseapp.util.Constants.Companion.POSTER_PATH_KEY
import com.br.thiago.themoviedatabaseapp.util.Constants.Companion.RELEASE_DATE_KEY
import com.br.thiago.themoviedatabaseapp.util.Constants.Companion.TITLE_KEY
import com.br.thiago.themoviedatabaseapp.util.Constants.Companion.VOTE_AVERAGE_KEY
import com.br.thiago.themoviedatabaseapp.util.toMovie
import com.parse.ParseObject
import com.parse.ParseQuery
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class DetailsPresenter(
    private val view: DetailsContract.View,
    private val movieService: MovieService
) : DetailsContract.Presenter {

    override fun getMovieDetails(movieId: Int, isFromDatabase: Boolean) {
        var movie = Movie()
        var isFavoriteMovie = false
        val query = ParseQuery.getQuery<ParseObject>("Movie")
        query.findInBackground { moviesFromParse, exception ->
            if (exception == null) {
                moviesFromParse.forEach {
                    if (it.getInt(ID_KEY) == movieId) {
                        movie.movieId = it.getInt(ID_KEY)
                        movie.backdropPath = it.getString(BACKDROP_PATH_KEY)
                        movie.originalTitle = it.getString(ORIGINAL_TITLE_KEY)
                        movie.posterPath = it.getString(POSTER_PATH_KEY)
                        movie.releaseDate = it.getString(RELEASE_DATE_KEY)
                        movie.title = it.getString(TITLE_KEY)
                        movie.voteAverage = it.getDouble(VOTE_AVERAGE_KEY)
                        isFavoriteMovie = true
                        view.setMovie(movie)
                        setupLayout(movie)
                        view.setFabAsFavoriteMovie()
                    }
                }
            } else {
                Log.d("parse", "getAllMovies: Error ${exception.message}")
            }
        }
        if (!isFavoriteMovie) {
            CoroutineScope(Dispatchers.IO).launch {
                val movieDetailsResponse = movieService.getMovieDetails(movieId).body()
                movieDetailsResponse?.toMovie()?.let {
                    movie = it
                    withContext(Dispatchers.Main) {
                        view.setMovie(movie)
                        view.setupLayout(movie)
                        view.hideLoadingScreen()
                    }
                }
            }
        }
    }

    override fun addOrRemoveFromParse(movie: Movie, isFavoriteMovie: Boolean) {
        if (isFavoriteMovie) {
            val query = ParseQuery.getQuery<ParseObject>("Movie")
            query.findInBackground { moviesFromParse, exception ->
                if (exception == null) {
                    moviesFromParse.forEach {
                        if (it.getInt(ID_KEY) == movie.movieId) {
                            it.deleteInBackground()
                        }
                    }
                } else {
                    Log.d("parse", "getAllMovies: Error ${exception.message}")
                }
            }
            view.setFabAsNotFavoriteMovie()
        } else {
            movie.saveInBackground()
            view.setFabAsFavoriteMovie()
        }
    }

    private fun setupLayout(movie: Movie?) {
        movie?.let {
            view.setupLayout(movie)
            view.hideLoadingScreen()
        }
    }

}