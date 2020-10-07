package id.owldevsoft.apotekseller.feature.masterdata.satuan.create

import android.content.Context
import id.owldevsoft.apotekseller.model.SatuanCreateBody
import id.owldevsoft.apotekseller.network.ApiProvider
import id.owldevsoft.apotekseller.network.Network
import id.owldevsoft.apotekseller.utils.PreferenceHelper

class CreateSatuanPresenter(val context: Context, var view: CreateSatuanContract.View?) :
    CreateSatuanContract.Presenter {

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

    override fun createSatuan(satuanCreateBody: SatuanCreateBody) {
        Network.request(context,
            call = ApiProvider.create()
                .createSatuan(apiKey!!, satuanCreateBody),
            success = {
                if (it.status == "success"){
                    view?.onCreateSuccess(it.message)
                } else{
                    view?.onCreateError(0, it.message)
                }
            },
            error = fun(t: Throwable, code: Int, message: String) {
                view?.onCreateError(code, message)
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