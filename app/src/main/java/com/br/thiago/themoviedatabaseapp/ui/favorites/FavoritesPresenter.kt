package com.br.thiago.themoviedatabaseapp.ui.favorites

import android.content.Context
import com.br.thiago.themoviedatabaseapp.local.MovieDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoritesPresenter(private val view: FavoritesContract.View) : FavoritesContract.Presenter {

    override fun getAllMovies(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            val movies = MovieDatabase.getInstance(context).getMovieDao().getAllMovies()
            withContext(Dispatchers.Main) {
                if (movies.isEmpty()) {
                    view.showNoFavoriteMovieText()
                } else {
                    view.showMovieList(movies)
                }
            }
        }
    }

}