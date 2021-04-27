package com.br.thiago.themoviedatabaseapp.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header

interface MovieService {

    @GET(test)
    suspend fun getMovies(
        @Header("api_key") api_key: String = KEY,
        @Header("language") language: String = "pt-BR",
        @Header("page") page: Int = 1
    ): Response<GetMoviesResponse>


    // List
    // https://api.themoviedb.org/3/movie/now_playing?api_key=f0ddbdbd9b527dc41f8f2c75c7e901f1&language=pt-BR&page=1

    // Details
    // https://api.themoviedb.org/3/movie/460465?api_key=f0ddbdbd9b527dc41f8f2c75c7e901f1&language=pt-BR

    // Image

    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/3/"
        private const val GET_MOVIES = "${BASE_URL}movie/now_playing?"
        private const val KEY = "f0ddbdbd9b527dc41f8f2c75c7e901f1"

        private const val test = "https://api.themoviedb.org/3/movie/now_playing?api_key=f0ddbdbd9b527dc41f8f2c75c7e901f1&language=pt-BR&page=1"


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