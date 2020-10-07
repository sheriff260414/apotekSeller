package id.owldevsoft.apotekseller.feature.register

import id.owldevsoft.apotekseller.base.BasePresenter
import id.owldevsoft.apotekseller.base.BaseView
import id.owldevsoft.apotekseller.model.RegisterBody
import id.owldevsoft.apotekseller.model.Response

interface RegisterContract {

    interface View: BaseView<Presenter> {
        fun onRegisterError(code: Int, message: String)
        fun onRegisterSuccess(response: Response)
        fun onProcess(boolean: Boolean)
    }
    interface Presenter : BasePresenter {
        fun doRegister(registerBody: RegisterBody)
    }

}