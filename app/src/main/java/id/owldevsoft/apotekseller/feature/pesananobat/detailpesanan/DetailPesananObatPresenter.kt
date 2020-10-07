package id.owldevsoft.apotekseller.feature.pesananobat.detailpesanan

import android.content.Context
import id.owldevsoft.apotekseller.feature.pesananobat.read.PesananObatContract
import id.owldevsoft.apotekseller.network.ApiProvider
import id.owldevsoft.apotekseller.network.Network
import id.owldevsoft.apotekseller.utils.PreferenceHelper

class DetailPesananObatPresenter(val context: Context, var view: DetailPesananObatContract.View?) : DetailPesananObatContract.Presenter {

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

    override fun getDetailPesanan(idPesanan: String) {
        Network.request(context, call = ApiProvider.create().getDetailPesananObat(apiKey!!, idPesanan),
            success = {
                if (it.status == "success") {
                    if (it.data.isNotEmpty()){
                        view?.onDetailSuccess(it.data)
                    }
                } else {
                    view?.onError(0, it.message)
                }
            },
            error = fun (_: Throwable, code: Int, message: String){
                view?.onError(code, message)
            },
            doOnSubscribe = {
                view?.onProcess(true)
            },
            doOnTerminate = {
                view?.onProcess(false)
            })
    }

}