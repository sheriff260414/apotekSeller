package id.owldevsoft.apotekseller.feature.masterdata.subgolonganobat.read

import android.content.Context
import id.owldevsoft.apotekseller.network.ApiProvider
import id.owldevsoft.apotekseller.network.Network
import id.owldevsoft.apotekseller.utils.PreferenceHelper

class SubGolonganObatPresenter(val context: Context, var view: SubGolonganObatContract.View?) : SubGolonganObatContract.Presenter {

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

    override fun getSubGolongan(idMember: String) {
        Network.request(context, call = ApiProvider.create().getDataSubGolonganObat(apiKey!!, idMember),
            success = {
                if (it.status == "success") {
                    if (it.data.isNotEmpty()){
                        view?.onSubGolonganSuccess(it.data)
                    }
                } else {
                    view?.onSubGolonganError(0, it.message)
                }
            },
            error = fun (_: Throwable, code: Int, message: String){
                view?.onSubGolonganError(code, message)
            },
            doOnSubscribe = {
                view?.onProcess(true)
            },
            doOnTerminate = {
                view?.onProcess(false)
            })
    }

}