package id.owldevsoft.apotekseller.feature.masterdata.jenisgolonganobat.delete

import android.content.Context
import id.owldevsoft.apotekseller.network.ApiProvider
import id.owldevsoft.apotekseller.network.Network
import id.owldevsoft.apotekseller.utils.PreferenceHelper

class DeleteJenisGolonganObatPresenter(val context: Context, var view: DeleteJenisGolonganObatContract.View?) :
    DeleteJenisGolonganObatContract.Presenter {

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

    override fun deleteJenisGolonganObat(idJenisGolonganObat: String) {
        Network.request(context, call = ApiProvider.create()
            .deleteJenisObat(apiKey!!, idJenisGolonganObat),
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