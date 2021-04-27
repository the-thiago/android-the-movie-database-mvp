package com.br.thiago.themoviedatabaseapp.api.getmovies

data class Result(
    val backdrop_path: String,
    val id: Int,
    val original_title: String,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val vote_average: Double,
)