package com.br.thiago.themoviedatabaseapp.api

import com.br.thiago.themoviedatabaseapp.api.getdetails.Movie
import com.br.thiago.themoviedatabaseapp.api.getmovies.GetMoviesResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET(GET_MOVIES_URL)
    suspend fun getMovies(): Response<GetMoviesResponse>

    @GET("${BASE_URL}movie/{id}?$KEY_AND_LANGUAGE")
    suspend fun getMovieDetails(@Path("id") movieId: Int): Response<Movie>

    @GET(SEARCH_MOVIES_URL)
    suspend fun searchMovies(@Query("query") query: String): Response<GetMoviesResponse>

    companion object {
        const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500"

        private const val BASE_URL = "https://api.themoviedb.org/3/"
        private const val PAGE = "&page=${1}"
        private const val KEY = "f0ddbdbd9b527dc41f8f2c75c7e901f1"
        private const val LANGUAGE = "en-US"
        private const val KEY_AND_LANGUAGE = "api_key=$KEY&language=$LANGUAGE"
        private const val GET_MOVIES_URL = "${BASE_URL}movie/now_playing?$KEY_AND_LANGUAGE$PAGE"
        private const val SEARCH_MOVIES_URL = "${BASE_URL}search/movie?api_key=$KEY"

        fun create(): MovieService {
            val logger =
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MovieService::class.java)
        }
    }

}