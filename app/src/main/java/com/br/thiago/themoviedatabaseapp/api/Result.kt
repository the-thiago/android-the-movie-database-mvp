package com.br.thiago.themoviedatabaseapp.api

data class Result(
    val backdrop_path: String, // Using
    val genre_ids: List<Int>,
    val id: Int, // Using
    val original_title: String, // Using
    val overview: String,
    val poster_path: String, // Using
    val release_date: String, // Using
    val title: String, // Using
    val vote_average: Double, // Using
)