package com.br.thiago.themoviedatabaseapp

import android.app.Application
import com.br.thiago.themoviedatabaseapp.model.Movie
import com.parse.Parse
import com.parse.ParseObject

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        parseInitialization()
    }

    private fun parseInitialization() {
        ParseObject.registerSubclass(Movie::class.java)
        Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG)
        Parse.initialize(
            Parse.Configuration.Builder(this)
                .applicationId("8lvCYIv9si0pHJO7S1mBrnfC6PBB4Y")
                .clientKey("5l8xNfOJq248qqTPZlrLOZaGWIO8kn")
                .server("https://parse-server-mob.herokuapp.com/parse")
                .build()
        )
    }

}