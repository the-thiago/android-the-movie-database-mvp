package com.br.thiago.themoviedatabaseapp.ui.login

import com.parse.ParseUser


class LoginPresenter(private var view: LoginContract.View?) : LoginContract.Presenter {

    private val usernameMissing = 200
    private val passwordMissing = 201
    private val userInvalidLoginParams = 101

    override fun login(user: String, password: String) {
        ParseUser.logInInBackground(user, password) { user, e ->
            if (user != null) {
                view?.onSuccessfulLogin()
            } else {
                if (e.code == usernameMissing || e.code == passwordMissing || e.code == userInvalidLoginParams) {
                    view?.wrongParametersErrorMessage()
                } else {
                    view?.unexpectedErrorMessage()
                }
            }
        }
    }

    override fun destroyView() {
        view = null
    }

    override fun onCurrentUserLogin() {
        val currentUser = ParseUser.getCurrentUser()
        if (currentUser != null) {
            // Disabled, while not implementing logout
//            view?.onSuccessfulLogin()
        }
    }

}