package com.br.thiago.themoviedatabaseapp.model

import com.br.thiago.themoviedatabaseapp.util.Constants.Companion.BACKDROP_PATH_KEY
import com.br.thiago.themoviedatabaseapp.util.Constants.Companion.ID_KEY
import com.br.thiago.themoviedatabaseapp.util.Constants.Companion.ORIGINAL_TITLE_KEY
import com.br.thiago.themoviedatabaseapp.util.Constants.Companion.POSTER_PATH_KEY
import com.br.thiago.themoviedatabaseapp.util.Constants.Companion.RELEASE_DATE_KEY
import com.br.thiago.themoviedatabaseapp.util.Constants.Companion.TITLE_KEY
import com.br.thiago.themoviedatabaseapp.util.Constants.Companion.VOTE_AVERAGE_KEY
import com.parse.ParseClassName
import com.parse.ParseObject

@ParseClassName("Movie")
class Movie : ParseObject() {

    var movieId: Int
        get() = getInt(ID_KEY)
        set(value) {
            put(ID_KEY, value)
        }

    var backdropPath: String?
        get() = getString(BACKDROP_PATH_KEY)
        set(value) {
            put(BACKDROP_PATH_KEY, value ?: "")
        }

    var originalTitle: String?
        get() = getString(ORIGINAL_TITLE_KEY)
        set(value) {
            put(ORIGINAL_TITLE_KEY, value ?: "")
        }

    var posterPath: String?
        get() = getString(POSTER_PATH_KEY)
        set(value) {
            put(POSTER_PATH_KEY, value ?: "")
        }

    var releaseDate: String?
        get() = getString(RELEASE_DATE_KEY)
        set(value) {
            put(RELEASE_DATE_KEY, value ?: "")
        }

    var title: String?
        get() = getString(TITLE_KEY)
        set(value) {
            put(TITLE_KEY, value ?: "")
        }

    var voteAverage: Double?
        get() = getDouble(VOTE_AVERAGE_KEY)
        set(value) {
            put(VOTE_AVERAGE_KEY, value ?: 0.0)
        }

}