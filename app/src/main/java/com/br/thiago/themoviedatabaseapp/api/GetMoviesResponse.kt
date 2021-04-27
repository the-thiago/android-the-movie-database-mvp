package com.br.thiago.themoviedatabaseapp.api

data class GetMoviesResponse(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)