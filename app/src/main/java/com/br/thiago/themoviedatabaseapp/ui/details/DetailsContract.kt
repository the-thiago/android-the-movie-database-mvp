package com.br.thiago.themoviedatabaseapp.ui.details

import com.br.thiago.themoviedatabaseapp.api.getdetails.GetMovieDetailsResponse

interface DetailsContract {

    interface View {
        fun setupLayout(details: GetMovieDetailsResponse)
        fun showLoadingScreen()
        fun hideLoadingScreen()
    }

    interface Presenter {
        fun getDetailsFromApi(movieId: Int)
    }

}