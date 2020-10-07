package id.owldevsoft.apotekseller.feature.masterdata.produsen.update

import android.content.Context
import id.owldevsoft.apotekseller.model.ProdusenUpdateBody
import id.owldevsoft.apotekseller.network.ApiProvider
import id.owldevsoft.apotekseller.network.Network
import id.owldevsoft.apotekseller.utils.PreferenceHelper

class UpdateProdusenPresenter(val context: Context, var view: UpdateProdusenContract.View?) :
    UpdateProdusenContract.Presenter {

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

    override fun updateProdusen(produsenUpdateBody: ProdusenUpdateBody) {
        Network.request(context,
            call = ApiProvider.create()
                .updateProdusen(apiKey!!, produsenUpdateBody),
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