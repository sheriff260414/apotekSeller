package id.owldevsoft.apotekseller.feature.masterdata.satuan.delete

import android.content.Context
import id.owldevsoft.apotekseller.network.ApiProvider
import id.owldevsoft.apotekseller.network.Network
import id.owldevsoft.apotekseller.utils.PreferenceHelper

class DeleteSatuanPresenter(val context: Context, var view: DeleteSatuanContract.View?) :
    DeleteSatuanContract.Presenter {

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

    override fun deleteSatuan(idSatuan: String) {
        Network.request(context, call = ApiProvider.create()
                .deleteSatuan(apiKey!!, idSatuan),
            success = {
                if (it.status == "success"){
                    view?.onDeleteSuccess(it.message)
                } else{
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