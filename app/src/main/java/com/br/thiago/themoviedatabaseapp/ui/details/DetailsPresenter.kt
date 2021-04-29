package com.br.thiago.themoviedatabaseapp.ui.details

import android.content.Context
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

class DetailsPresenter(private val view: DetailsContract.View) : DetailsContract.Presenter {

    var movie: Movie? = null

    override fun getMovieDetails(movieId: Int, isFromDatabase: Boolean, context: Context) {
        var movie = Movie()
        CoroutineScope(Dispatchers.IO).launch {
            if (isFromDatabase) {

                val query = ParseQuery.getQuery<ParseObject>("Movie")
                query.findInBackground { moviesFromParse, exception ->
                    if (exception == null) {
                        val movies = mutableListOf<Movie>()
                        moviesFromParse.forEach {
                            if (it.getInt(ID_KEY) == movieId) {
                                movie.movieId = it.getInt(ID_KEY)
                                movie.backdropPath = it.getString(BACKDROP_PATH_KEY)
                                movie.originalTitle = it.getString(ORIGINAL_TITLE_KEY)
                                movie.posterPath = it.getString(POSTER_PATH_KEY)
                                movie.releaseDate = it.getString(RELEASE_DATE_KEY)
                                movie.title = it.getString(TITLE_KEY)
                                movie.voteAverage = it.getDouble(VOTE_AVERAGE_KEY)
                                movies.add(movie)
                            }
                        }
                    } else {
                        Log.d("parseTest", "getAllMovies: Error ${exception.message}")
                    }
                }

                setupLayout(movie)
                view.setFabAsFavoriteMovie()
            } else {
                val movieDetailsResponse = MovieService.create().getMovieDetails(movieId).body()
                movieDetailsResponse?.toMovie()?.let {
                    movie = it
                    setupLayout(movie)
                }
            }
            this@DetailsPresenter.movie = movie
        }
    }

    override fun addOrRemoveFromDatabase(movie: Movie, isFavoriteMovie: Boolean, context: Context) {
//        CoroutineScope(Dispatchers.IO).launch {
//            if (isFavoriteMovie) {
////                MovieDatabase.getInstance(context).getMovieDao().deleteMovie(movie)
//                withContext(Dispatchers.Main) {
//                    view.setFabAsFavoriteMovie()
//                }
//            } else {
////                MovieDatabase.getInstance(context).getMovieDao().insertMovie(movie)
//                withContext(Dispatchers.Main) {
//                    view.setFabAsNotFavoriteMovie()
//                }
//            }
//        }
    }

    override fun isFavoriteMovie(isFromDatabase: Boolean, context: Context): Boolean {
//        if (isFromDatabase) {
//            return true
//        }
////        CoroutineScope(Dispatchers.IO).launch {
////            MovieDatabase.getInstance(context).getMovieDao()
////        }
        return false
    }

    private suspend fun setupLayout(movie: Movie?) {
        movie?.let {
            withContext(Dispatchers.Main) {
                view.setupLayout(movie)
                view.hideLoadingScreen()
            }
        }
    }

}