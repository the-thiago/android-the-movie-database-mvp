package com.br.thiago.themoviedatabaseapp.ui.search

import com.br.thiago.themoviedatabaseapp.api.MovieService
import com.br.thiago.themoviedatabaseapp.model.Movie
import com.br.thiago.themoviedatabaseapp.util.toMovies
import kotlinx.coroutines.*

class SearchPresenter(private val view: SearchContract.View) : SearchContract.Presenter {

    var searchJob: Job? = null

    override fun searchMovies(query: String) {
        searchJob?.cancel()
        view.showLoadingScreen()
        searchJob = CoroutineScope(Dispatchers.IO).launch {
            var movies = emptyList<Movie>()
            val moviesRequest = MovieService.create().searchMovies(query)
            if (moviesRequest.isSuccessful) {
                moviesRequest.body()?.toMovies()?.let {
                    movies = it
                }
                withContext(Dispatchers.Main) {
                    view.hideLoadingScreen()
                    if (movies.isEmpty()) {
                        view.showEmptySearch()
                    } else {
                        view.showSearchedMovies(movies)
                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    view.showEmptySearch()
                    view.hideLoadingScreen()
                }
            }
        }
    }

}