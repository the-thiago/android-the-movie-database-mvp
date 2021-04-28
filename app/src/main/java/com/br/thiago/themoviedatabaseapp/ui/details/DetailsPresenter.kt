package com.br.thiago.themoviedatabaseapp.ui.details

import com.br.thiago.themoviedatabaseapp.api.MovieService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailsPresenter(private val view: DetailsContract.View) : DetailsContract.Presenter {

    override fun getDetailsFromApi(movieId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            view.showLoadingScreen()
            val details = MovieService.create().getMovieDetails(movieId).body()
            details?.let {
                withContext(Dispatchers.Main) {
                    view.setupLayout(details)
                    view.hideLoadingScreen()
                }
            }
        }
    }

}