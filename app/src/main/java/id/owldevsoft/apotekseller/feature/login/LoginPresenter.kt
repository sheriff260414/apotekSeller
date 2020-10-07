package id.owldevsoft.apotekseller.feature.login

import android.content.Context
import id.owldevsoft.apotekseller.model.LoginBody
import id.owldevsoft.apotekseller.network.ApiProvider
import id.owldevsoft.apotekseller.network.Network

class LoginPresenter(val context: Context, var view: LoginContract.View?) :
    LoginContract.Presenter {

    init {
        view?.presenter = this
    }

    override fun start() {

    }

    override fun destroy() {
        view = null
    }

    override fun doLogin(loginBody: LoginBody) {
        Network.request(
            context, call = ApiProvider.create().postLogin(loginBody),
            success = {
                if (it.status == "success") {
                    view?.onLoginSuccess(it)
                } else {
                    view?.onLoginError(0, it.message)
                }
            },
            error = fun(t: Throwable, code: Int,message: String) {
                view?.onLoginError(code, message)
            },
            doOnSubscribe = {
                view?.onProcess(true)
            },
            doOnTerminate = {
                view?.onProcess(false)
            })
    }
}