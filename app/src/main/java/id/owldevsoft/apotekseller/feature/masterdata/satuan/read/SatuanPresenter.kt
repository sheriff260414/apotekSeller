package id.owldevsoft.apotekseller.feature.masterdata.satuan.read

import android.content.Context
import id.owldevsoft.apotekseller.network.ApiProvider
import id.owldevsoft.apotekseller.network.Network
import id.owldevsoft.apotekseller.utils.PreferenceHelper

class SatuanPresenter(val context: Context, var view: SatuanContract.View?) : SatuanContract.Presenter {

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

    override fun getSatuan(idMember: String) {
        Network.request(context, call = ApiProvider.create().getDataSatuan(apiKey!!, idMember),
            success = {
                if (it.status == "success") {
                    if (it.data.isNotEmpty()){
                        view?.onSatuanSuccess(it.data)
                    }
                } else {
                    view?.onSatuanError(0, it.message)
                }
            },
            error = fun (_: Throwable, code: Int, message: String){
                view?.onSatuanError(code, message)
            },
            doOnSubscribe = {
                view?.onProcess(true)
            },
            doOnTerminate = {
                view?.onProcess(false)
            })
    }

}