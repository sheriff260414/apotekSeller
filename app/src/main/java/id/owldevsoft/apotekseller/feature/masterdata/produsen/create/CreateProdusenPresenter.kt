package id.owldevsoft.apotekseller.feature.masterdata.produsen.create

import android.content.Context
import id.owldevsoft.apotekseller.model.ProdusenCreateBody
import id.owldevsoft.apotekseller.network.ApiProvider
import id.owldevsoft.apotekseller.network.Network
import id.owldevsoft.apotekseller.utils.PreferenceHelper

class CreateProdusenPresenter(val context: Context, var view: CreateProdusenContract.View?) :
    CreateProdusenContract.Presenter {

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

    override fun createProdusen(produsenCreateBody: ProdusenCreateBody) {
        Network.request(context,
            call = ApiProvider.create()
                .createProdusen(apiKey!!, produsenCreateBody),
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