package id.owldevsoft.apotekseller.feature.masterdata.subgolonganobat.delete

import android.content.Context
import id.owldevsoft.apotekseller.network.ApiProvider
import id.owldevsoft.apotekseller.network.Network
import id.owldevsoft.apotekseller.utils.PreferenceHelper

class DeleteSubGolonganObatPresenter(val context: Context, var view: DeleteSubGolonganObatContract.View?) :
    DeleteSubGolonganObatContract.Presenter {

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

    override fun deleteSubGolonganObat(idSubGolonganObat: String) {
        Network.request(context, call = ApiProvider.create()
            .deleteSubGolonganObat(apiKey!!, idSubGolonganObat),
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