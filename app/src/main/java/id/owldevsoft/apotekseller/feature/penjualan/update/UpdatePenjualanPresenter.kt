package id.owldevsoft.apotekseller.feature.penjualan.update

import android.content.Context
import id.owldevsoft.apotekseller.feature.masterdata.jenisgolonganobat.update.UpdateJenisGolonganObatContract
import id.owldevsoft.apotekseller.model.AddPenjualanBody
import id.owldevsoft.apotekseller.model.JenisObatUpdateBody
import id.owldevsoft.apotekseller.network.ApiProvider
import id.owldevsoft.apotekseller.network.Network
import id.owldevsoft.apotekseller.utils.PreferenceHelper

class UpdatePenjualanPresenter(val context: Context, var view: UpdatePenjualanContract.View?) :
    UpdatePenjualanContract.Presenter {

    var apiKey : String? = null
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

    override fun updatePenjualan(updatePenjualanBody: AddPenjualanBody.updatePenjualanBody) {
        Network.request(context,
            call = ApiProvider.create()
                .updatePenjualan(apiKey!!, updatePenjualanBody),
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