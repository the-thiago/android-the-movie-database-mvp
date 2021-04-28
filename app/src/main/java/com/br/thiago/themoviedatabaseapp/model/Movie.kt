package com.br.thiago.themoviedatabaseapp.model

data class Movie(
    val backdrop_path: String? = "",
    val id: Int,
    val original_title: String,
    val poster_path: String? = "",
    val release_date: String,
    val title: String,
    val vote_average: Double,
)