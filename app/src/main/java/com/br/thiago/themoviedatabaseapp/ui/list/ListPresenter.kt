package com.br.thiago.themoviedatabaseapp.ui.list

import com.br.thiago.themoviedatabaseapp.api.MovieService
import com.br.thiago.themoviedatabaseapp.model.Movie
import com.br.thiago.themoviedatabaseapp.util.getMovies
import kotlinx.coroutines.*

class ListPresenter(private val view: ListContract.View) : ListContract.Presenter {

    override fun getMoviesFromApi() {
        view.showLoadingScreen()
        CoroutineScope(Dispatchers.IO).launch {
            var movies = emptyList<Movie>()
            val moviesRequest = MovieService.create().getMovies()
            if (moviesRequest.isSuccessful) {
                moviesRequest.body()?.getMovies()?.let {
                    movies = it
                }
                withContext(Dispatchers.Main) {
                    view.showMovieList(movies)
                    view.hideLoadingScreen()
                }
            }
        }
    }

}