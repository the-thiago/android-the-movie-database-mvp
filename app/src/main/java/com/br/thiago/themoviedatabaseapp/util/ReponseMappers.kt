package com.br.thiago.themoviedatabaseapp.util

import com.br.thiago.themoviedatabaseapp.api.getmovies.GetMoviesResponse
import com.br.thiago.themoviedatabaseapp.model.Movie

fun GetMoviesResponse.getMovies(): List<Movie> {
    return results.map {
        Movie(
            id = it.id,
            title = it.title,
            backdrop_path = it.backdrop_path,
            original_title = it.original_title,
            poster_path = it.poster_path,
            release_date = it.release_date,
            vote_average = it.vote_average
        )
    }
}