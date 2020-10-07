package id.owldevsoft.apotekseller.feature.masterdata.subgolonganobat.update

import android.content.Context
import id.owldevsoft.apotekseller.model.SubGolonganObatUpdateBody
import id.owldevsoft.apotekseller.network.ApiProvider
import id.owldevsoft.apotekseller.network.Network
import id.owldevsoft.apotekseller.utils.PreferenceHelper

class UpdateSubGolonganObatPresenter(val context: Context, var view: UpdateSubGolonganObatContract.View?) :
    UpdateSubGolonganObatContract.Presenter {

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

    override fun updateSatuan(subGolonganObatUpdateBody: SubGolonganObatUpdateBody) {
        Network.request(context,
            call = ApiProvider.create()
                .updateSubGolonganObat(apiKey!!, subGolonganObatUpdateBody),
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