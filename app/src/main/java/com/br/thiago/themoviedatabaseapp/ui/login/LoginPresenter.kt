package com.br.thiago.themoviedatabaseapp.ui.login

import com.parse.ParseUser


class LoginPresenter(private var view: LoginContract.View?) : LoginContract.Presenter {

    override fun login(user: String, password: String) {
        ParseUser.logInInBackground(user, password) { user, e ->
            if (user != null) {
                view?.onSuccessfulLogin()
            } else {
                // Signup failed. Look at the ParseException to see what happened.
            }
        }
    }

    override fun destroyView() {
        view = null
    }

    override fun onCurrentUserLogin() {
        val currentUser = ParseUser.getCurrentUser()
        if (currentUser != null) {
            view?.onSuccessfulLogin()
        }
    }

}