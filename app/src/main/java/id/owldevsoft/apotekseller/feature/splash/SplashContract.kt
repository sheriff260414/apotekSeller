package id.owldevsoft.apotekseller.feature.splash

import id.owldevsoft.apotekseller.base.BasePresenter
import id.owldevsoft.apotekseller.base.BaseView

class SplashContract {

    interface View: BaseView<Presenter> {
        fun onError(code: Int, message: String)
        fun onBannerSuccess(message: String)
        fun onProcess(boolean: Boolean)
    }

    interface Presenter : BasePresenter {
        fun getBanner()
    }

}