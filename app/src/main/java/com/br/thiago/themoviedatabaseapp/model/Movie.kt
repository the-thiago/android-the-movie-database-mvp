package com.br.thiago.themoviedatabaseapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val backdrop_path: String? = "",
    val original_title: String,
    val poster_path: String? = "",
    val release_date: String,
    val title: String,
    val vote_average: Double,
)