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
    val titleFromApi = this.title
    val overviewFromApi = this.overview
    val budgetFromApi = this.budget
    val revenueFromApi = this.revenue
    val runtimeFromApi = this.runtime
    val statusFromApi = this.status
    return Movie().apply {
        // overview, budget, revenue, runtime, status (released)
        movieId = id
        backdropPath = backdrop_path
        posterPath = poster_path
        voteAverage = vote_average
        this.title = titleFromApi
        releaseDate = release_date
        originalTitle = original_title
        this.overview = overviewFromApi
        this.budget = budgetFromApi
        this.revenue = revenueFromApi
        this.runtime = runtimeFromApi
        this.status = statusFromApi
    }
}