package com.br.thiago.themoviedatabaseapp.util

import com.br.thiago.themoviedatabaseapp.api.getmovies.GetMoviesResponse
import com.br.thiago.themoviedatabaseapp.model.Movie

fun GetMoviesResponse.toMovies(): List<Movie> {
    return results.map {
        Movie().apply {
            movieId = it.id
            backdropPath = it.backdrop_path
            posterPath = it.poster_path
            voteAverage = it.vote_average
            title = it.title
            releaseDate = it.release_date
            originalTitle = it.original_title
        }
    }
}

fun com.br.thiago.themoviedatabaseapp.api.getdetails.Movie.toMovie(): Movie {
    return Movie(
//        id = id,
//        backdrop_path = backdrop_path,
//        original_title = original_title,
//        poster_path = poster_path,
//        release_date = release_date,
//        title = title,
//        vote_average = vote_average
    )
}