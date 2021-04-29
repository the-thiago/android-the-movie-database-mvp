package com.br.thiago.themoviedatabaseapp.ui.favorites

import android.util.Log
import com.br.thiago.themoviedatabaseapp.model.Movie
import com.br.thiago.themoviedatabaseapp.util.Constants.Companion.BACKDROP_PATH_KEY
import com.br.thiago.themoviedatabaseapp.util.Constants.Companion.ID_KEY
import com.br.thiago.themoviedatabaseapp.util.Constants.Companion.ORIGINAL_TITLE_KEY
import com.br.thiago.themoviedatabaseapp.util.Constants.Companion.POSTER_PATH_KEY
import com.br.thiago.themoviedatabaseapp.util.Constants.Companion.RELEASE_DATE_KEY
import com.br.thiago.themoviedatabaseapp.util.Constants.Companion.TITLE_KEY
import com.br.thiago.themoviedatabaseapp.util.Constants.Companion.VOTE_AVERAGE_KEY
import com.parse.ParseObject
import com.parse.ParseQuery

class FavoritesPresenter(private val view: FavoritesContract.View) : FavoritesContract.Presenter {

    override fun getAllMovies() {
        val query = ParseQuery.getQuery<ParseObject>("Movie")
        query.findInBackground { moviesFromParse, exception ->
            if (exception == null) {
                if (moviesFromParse.isEmpty()) {
                    view.showNoFavoriteMovieText()
                } else {
                    val movies = mutableListOf<Movie>()
                    moviesFromParse.forEach {
                        val movie = Movie()
                        movie.movieId = it.getInt(ID_KEY)
                        movie.backdropPath = it.getString(BACKDROP_PATH_KEY)
                        movie.originalTitle = it.getString(ORIGINAL_TITLE_KEY)
                        movie.posterPath = it.getString(POSTER_PATH_KEY)
                        movie.releaseDate = it.getString(RELEASE_DATE_KEY)
                        movie.title = it.getString(TITLE_KEY)
                        movie.voteAverage = it.getDouble(VOTE_AVERAGE_KEY)
                        movies.add(movie)
                    }
                    view.showMovieList(movies)
                }
            } else {
                Log.d("parse", "getAllMovies: Error ${exception.message}")
            }
        }
    }

}