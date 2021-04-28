package com.br.thiago.themoviedatabaseapp.local

import androidx.room.*
import com.br.thiago.themoviedatabaseapp.model.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: Movie): Long

    @Query("SELECT * FROM movies")
    fun getAllMovies(): List<Movie>

    @Delete
    suspend fun deleteMovie(movie: Movie)

}