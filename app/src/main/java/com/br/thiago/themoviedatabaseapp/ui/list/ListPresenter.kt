package com.br.thiago.themoviedatabaseapp.ui.list

import com.br.thiago.themoviedatabaseapp.api.MovieService
import com.br.thiago.themoviedatabaseapp.model.Movie
import com.br.thiago.themoviedatabaseapp.util.toMovies
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListPresenter(private var view: ListContract.View?) : ListContract.Presenter {

    private var moviesPage = 1
    var movies = mutableListOf<Movie>()

    override fun getMoviesFromApi() {
        CoroutineScope(Dispatchers.IO).launch {
            val moviesRequest = MovieService.create().getMovies(moviesPage)
            if (moviesRequest.isSuccessful) {
                moviesRequest.body()?.toMovies()?.let {
                    moviesPage++
                    movies.addAll(it)
                }
                withContext(Dispatchers.Main) {
                    view?.showMovieList(movies)
                }
            }
        }
    }

    override fun destroyView() {
        view = null
    }

}