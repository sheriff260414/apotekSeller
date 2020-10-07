package id.owldevsoft.apotekseller.feature.penjualan.read

import android.content.Context
import id.owldevsoft.apotekseller.network.ApiProvider
import id.owldevsoft.apotekseller.network.Network
import id.owldevsoft.apotekseller.utils.PreferenceHelper

class PenjualanPresenter(val context: Context, var view: PenjualanContract.View?) : PenjualanContract.Presenter {

    var apiKey: String? = null
    private val preferenceHelper = PreferenceHelper(context)

    init {
        view?.presenter = this
        apiKey = preferenceHelper.getUser()!!.data.user[0].apiKey
    }

    override fun start() {

    }

    override fun destroy() {
        view = null
    }

    override fun getPenjualan(idMember: String) {
        Network.request(context, call = ApiProvider.create().getPenjualan(apiKey!!, idMember),
            success = {
                if (it.status == "success") {
                    if (it.data.isNotEmpty()){
                        view?.onPenjualanSuccess(it.data)
                    }
                } else {
                    view?.onPenjualanError(0, it.message)
                }
            },
            error = fun (_: Throwable, code: Int, message: String){
                view?.onPenjualanError(code, message)
            },
            doOnSubscribe = {
                view?.onProcess(true)
            },
            doOnTerminate = {
                view?.onProcess(false)
            })
    }

}