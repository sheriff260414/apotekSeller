package id.owldevsoft.apotekseller.feature.masterproduk.read

import android.content.Context
import id.owldevsoft.apotekseller.network.ApiProvider
import id.owldevsoft.apotekseller.network.Network
import id.owldevsoft.apotekseller.utils.PreferenceHelper

class DataObatPresenter(val context: Context, var view: DataObatContract.View?) : DataObatContract.Presenter {

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

    override fun getObat(idMember: String) {
        Network.request(context, call = ApiProvider.create().getObat(apiKey!!, idMember),
            success = {
                if (it.status == "success") {
                    if (it.data.isNotEmpty()){
                        view?.onObatSuccess(it.data)
                    }
                } else {
                    view?.onObatError(0, it.message)
                }
            },
            error = fun (_: Throwable, code: Int, message: String){
                view?.onObatError(code, message)
            },
            doOnSubscribe = {
                view?.onProcess(true)
            },
            doOnTerminate = {
                view?.onProcess(false)
            })
    }

}