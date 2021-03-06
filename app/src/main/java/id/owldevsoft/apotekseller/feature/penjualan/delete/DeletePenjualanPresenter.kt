package id.owldevsoft.apotekseller.feature.penjualan.delete

import android.content.Context
import id.owldevsoft.apotekseller.feature.masterdata.subgolonganobat.delete.DeleteSubGolonganObatContract
import id.owldevsoft.apotekseller.network.ApiProvider
import id.owldevsoft.apotekseller.network.Network
import id.owldevsoft.apotekseller.utils.PreferenceHelper

class DeletePenjualanPresenter(val context: Context, var view: DeletePenjualanContract.View?) :
    DeletePenjualanContract.Presenter {

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

    override fun deletePenjualan(idObatMobile: String) {
        Network.request(context, call = ApiProvider.create()
            .deletePenjualan(apiKey!!, idObatMobile),
            success = {
                if (it.status == "success") {
                    view?.onDeleteSuccess(it.message)
                } else {
                    view?.onDeleteError(0, it.message)
                }
            },
            error = fun(t: Throwable, code: Int, message: String) {
                view?.onDeleteError(code, message)
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