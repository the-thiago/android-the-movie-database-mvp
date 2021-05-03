package com.br.thiago.themoviedatabaseapp.ui.list

import android.net.ConnectivityManager
import com.br.thiago.themoviedatabaseapp.api.MovieService
import com.br.thiago.themoviedatabaseapp.model.Movie
import com.br.thiago.themoviedatabaseapp.util.hasInternetConnection
import com.br.thiago.themoviedatabaseapp.util.toMovies
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NowPlayingPresenter(private var view: NowPlayingContract.View?) :
    NowPlayingContract.Presenter {

    private var moviesPage = 1
    var movies = mutableListOf<Movie>()

    override fun getMoviesFromApi(connectivityManager: ConnectivityManager) {
        if (hasInternetConnection(connectivityManager)) {
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
        } else {
            view?.showNoInternetConnectionWarning()
        }
    }

    override fun destroyView() {
        view = null
    }

}