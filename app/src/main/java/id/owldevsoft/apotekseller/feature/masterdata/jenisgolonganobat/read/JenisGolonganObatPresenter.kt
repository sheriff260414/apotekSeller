package id.owldevsoft.apotekseller.feature.masterdata.jenisgolonganobat.read

import android.content.Context
import id.owldevsoft.apotekseller.network.ApiProvider
import id.owldevsoft.apotekseller.network.Network
import id.owldevsoft.apotekseller.utils.PreferenceHelper

class JenisGolonganObatPresenter(val context: Context, var view: JenisGolonganObatContract.View?) :
    JenisGolonganObatContract.Presenter {

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

    override fun getJenisGolonganObat(idMember: String) {
        Network.request(context,
            call = ApiProvider.create()
                .getDataJenisObat(apiKey!!, idMember),
            success = {
                if (it.status == "success") {
                    if (it.data.isNotEmpty()) {
                        view?.onJenisGolonganSuccess(it.data)
                    }
                } else {
                    view?.onJenisGolonganError(0, it.message)
                }
            },
            error = fun(_: Throwable, code: Int, message: String) {
                view?.onJenisGolonganError(code, message)
            },
            doOnSubscribe = {
                view?.onProcess(true)
            },
            doOnTerminate = {
                view?.onProcess(false)
            })
    }

}