package id.owldevsoft.apotekseller.feature.pesananresep.updatestatus

import android.content.Context
import id.owldevsoft.apotekseller.model.PesananResepUpdateStatusBody
import id.owldevsoft.apotekseller.network.ApiProvider
import id.owldevsoft.apotekseller.network.Network
import id.owldevsoft.apotekseller.utils.PreferenceHelper

class UpdateStatusPesananResepPresenter(val context: Context, var view: UpdateStatusPesananResepContract.View?) :
    UpdateStatusPesananResepContract.Presenter {

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

    override fun updateStatusPesananResep(pesananResepUpdateStatusBody: PesananResepUpdateStatusBody) {
        Network.request(context, call = ApiProvider.create()
            .updateStatusPesananResep(apiKey!!, pesananResepUpdateStatusBody),
            success = {
                if (it.status == "success") {
                    view?.onUpdateSuccess(it.message)
                } else {
                    view?.onError(0, it.message)
                }
            },
            error = fun(t: Throwable, code: Int, message: String) {
                view?.onError(code, message)
            },
            doOnSubscribe = {
                view?.onProcess(true)
            },
            doOnTerminate = {
                view?.onProcess(false)
            }
        )
    }
}