package id.owldevsoft.apotekseller.feature.pesananobat.updatestatus

import android.content.Context
import id.owldevsoft.apotekseller.feature.penjualan.delete.DeletePenjualanContract
import id.owldevsoft.apotekseller.model.PesananObatUpdateStatusBody
import id.owldevsoft.apotekseller.network.ApiProvider
import id.owldevsoft.apotekseller.network.Network
import id.owldevsoft.apotekseller.utils.PreferenceHelper

class UpdateStatusPesananObatPresenter(val context: Context, var view: UpdateStatusPesananObatContract.View?) :
    UpdateStatusPesananObatContract.Presenter {

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

    override fun updateStatusPesananObat(pesananObatUpdateStatusBody: PesananObatUpdateStatusBody) {
        Network.request(context, call = ApiProvider.create()
            .updateStatusPesananObat(apiKey!!, pesananObatUpdateStatusBody),
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