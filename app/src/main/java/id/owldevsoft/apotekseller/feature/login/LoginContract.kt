package id.owldevsoft.apotekseller.feature.login

import id.owldevsoft.apotekseller.base.BasePresenter
import id.owldevsoft.apotekseller.base.BaseView
import id.owldevsoft.apotekseller.model.LoginBody
import id.owldevsoft.apotekseller.model.LoginResponse

interface LoginContract {
    interface View: BaseView<Presenter>{
        fun onLoginError(code: Int, message: String)
        fun onLoginSuccess(loginResponse: LoginResponse)
        fun onProcess(boolean: Boolean)
    }
    interface Presenter : BasePresenter {
        fun doLogin(loginBody: LoginBody)
    }
}