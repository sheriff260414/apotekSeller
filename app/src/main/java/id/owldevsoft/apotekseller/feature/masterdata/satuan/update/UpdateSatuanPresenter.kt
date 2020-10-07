package id.owldevsoft.apotekseller.feature.masterdata.satuan.update

import android.content.Context
import id.owldevsoft.apotekseller.model.SatuanUpdateBody
import id.owldevsoft.apotekseller.network.ApiProvider
import id.owldevsoft.apotekseller.network.Network
import id.owldevsoft.apotekseller.utils.PreferenceHelper

class UpdateSatuanPresenter(val context: Context, var view: UpdateSatuanContract.View?) :
    UpdateSatuanContract.Presenter {

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

    override fun updateSatuan(satuanUpdateBody: SatuanUpdateBody) {
        Network.request(context,
            call = ApiProvider.create()
                .updateSatuan(apiKey!!, satuanUpdateBody),
            success = {
                if (it.status == "success"){
                    view?.onUpdateSuccess(it.message)
                } else{
                    view?.onUpdateError(0, it.message)
                }
            },
            error = fun(t: Throwable, code: Int, message: String) {
                view?.onUpdateError(code, message)
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