package id.owldevsoft.apotekseller.feature.home

import android.content.Context
import id.owldevsoft.apotekseller.network.ApiProvider
import id.owldevsoft.apotekseller.network.Network
import id.owldevsoft.apotekseller.utils.PreferenceHelper

class HomePresenter(val context: Context, var view: HomeContract.View?) : HomeContract.Presenter {

    var apiKey: String? = null
    private val preferenceHelper = PreferenceHelper(context)

    init {
        view?.presenter = this
        apiKey = preferenceHelper.getUser()?.data?.user?.get(0)?.apiKey
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
                        if (it.data.isNotEmpty()) {
                            view?.onBannerSuccess(it)
                        }
                    } else {
                        view?.onError(0, it.message)
                    }
                },
                error = fun(_: Throwable, code: Int, message: String) {
                    view?.onError(code, message)
                },
                doOnSubscribe = {

                },
                doOnTerminate = {

                })
        }
    }

    override fun getPesanan(idMember: String) {
        apiKey?.let { ApiProvider.create().getPesananObat(it, idMember) }?.let {
            Network.request(context, call = it,
                success = {
                    if (it.status == "success") {
                        if (it.data.isNotEmpty()) {
                            view?.onPesananSuccess(it.data)
                        }
                    } else {
                        view?.onError(0, it.message)
                    }
                },
                error = fun(_: Throwable, code: Int, message: String) {
                },
                doOnSubscribe = {
                },
                doOnTerminate = {
                })
        }
    }

    override fun getResep(idMember: String) {
        Network.request(context, call = ApiProvider.create().getPesananResep(apiKey!!, idMember),
            success = {
                if (it.status == "success") {
                    if (it.data.isNotEmpty()){
                        view?.onResepSuccess(it.data)
                    }
                } else {
                    view?.onError(0, it.message)
                }
            },
            error = fun (_: Throwable, code: Int, message: String){
                view?.onError(code, message)
            },
            doOnSubscribe = {
            },
            doOnTerminate = {
            })
    }
}