package id.owldevsoft.apotekseller.feature.penjualan.create

import android.content.Context
import id.owldevsoft.apotekseller.model.AddPenjualanBody
import id.owldevsoft.apotekseller.network.ApiProvider
import id.owldevsoft.apotekseller.network.Network
import id.owldevsoft.apotekseller.utils.PreferenceHelper

class AddPenjualanPresenter(val context: Context, var view: AddPenjualanContract.View?) :
    AddPenjualanContract.Presenter {

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

    override fun addPenjualan(addPenjualanBody: AddPenjualanBody) {
        Network.request(context,
            call = ApiProvider.create()
                .createPenjualan(apiKey!!, addPenjualanBody),
            success = {
                if (it.status == "success") {
                    view?.onAddPenjualanSuccess(it)
                } else {
                    view?.onError(0, it.message)
                }
            },
            error = fun(_: Throwable, code: Int, message: String) {
                view?.onError(code, message)
            },
            doOnSubscribe = {
                view?.onProcess(true)
            },
            doOnTerminate = {
                view?.onProcess(false)
            })
    }

    override fun getObat(idMember: String) {
        Network.request(context, call = ApiProvider.create().getObat(apiKey!!, idMember),
            success = {
                if (it.status == "success") {
                    if (it.data.isNotEmpty()) {
                        view?.onObatSuccess(it.data)
                    }
                } else {
                    view?.onError(0, it.message)
                }
            },
            error = fun(_: Throwable, code: Int, message: String) {
                view?.onError(code, message)
            },
            doOnSubscribe = {
                view?.onProcessGet(true)
            },
            doOnTerminate = {
                view?.onProcessGet(false)
            })
    }

    override fun getSatuan(idMember: String) {
        Network.request(context, call = ApiProvider.create().getDataSatuan(apiKey!!, idMember),
            success = {
                if (it.status == "success") {
                    if (it.data.isNotEmpty()) {
                        view?.onSatuanSuccess(it.data)
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