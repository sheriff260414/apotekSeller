package id.owldevsoft.apotekseller.feature.splash

import android.content.Context
import id.owldevsoft.apotekseller.feature.home.HomeContract
import id.owldevsoft.apotekseller.network.ApiProvider
import id.owldevsoft.apotekseller.network.Network
import id.owldevsoft.apotekseller.utils.PreferenceHelper

class SplashPresenter(val context: Context, var view: SplashContract.View?) :
    SplashContract.Presenter {

    var apiKey: String? = null
    private val preferenceHelper = PreferenceHelper(context)

    init {
        view?.presenter = this
        if (preferenceHelper.getUser() != null) {
            apiKey = preferenceHelper.getUser()?.data?.user?.get(0)?.apiKey
        } else{
            apiKey = ""
        }
    }

    override fun start() {

    }

    override fun destroy() {
        view = null
    }

    override fun getBanner() {
        apiKey?.let { ApiProvider.create().getBanner(it) }?.let {
            Network.request(context, call = it,
                success = {
                    if (it.status == "success") {
                        view?.onBannerSuccess(it.message)
                    } else {
                        view?.onError(0, it.message)
                    }
                },
                error = fun(t: Throwable, code: Int, message: String) {
                    if (code == 401){
                        view?.onError(code, message)
                    } else{
                        view?.onError(code, message)
                    }
                },
                doOnSubscribe = {

                },
                doOnTerminate = {

                })
        }
    }

}