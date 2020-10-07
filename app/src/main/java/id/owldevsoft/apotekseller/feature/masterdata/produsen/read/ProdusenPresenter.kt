package id.owldevsoft.apotekseller.feature.masterdata.produsen.read

import android.content.Context
import id.owldevsoft.apotekseller.network.ApiProvider
import id.owldevsoft.apotekseller.network.Network
import id.owldevsoft.apotekseller.utils.PreferenceHelper

class ProdusenPresenter(val context: Context, var view: ProdusenContract.View?) :
    ProdusenContract.Presenter {

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

    override fun getProdusen(idMember: String) {
        Network.request(context, call = ApiProvider.create().getDataProdusen(apiKey!!, idMember),
            success = {
                if (it.status == "success") {
                    if (it.data.isNotEmpty()) {
                        view?.onMerkSuccess(it.data)
                    }
                } else {
                    view?.onMerkError(0, it.message)
                }
            },
            error = fun(_: Throwable, code: Int, message: String) {
                view?.onMerkError(code, message)
            },
            doOnSubscribe = {
                view?.onProcess(true)
            },
            doOnTerminate = {
                view?.onProcess(false)
            })
    }

}