package id.owldevsoft.apotekseller.feature.pesananresep.read

import id.owldevsoft.apotekseller.base.BasePresenter
import id.owldevsoft.apotekseller.base.BaseView
import id.owldevsoft.apotekseller.model.PesananResepResponse

interface PesananResepContract {

    interface View: BaseView<Presenter> {
        fun onError(code: Int, message: String)
        fun onPesananSuccess(it: ArrayList<PesananResepResponse.Data>)
        fun onProcess(boolean: Boolean)
    }

    interface Presenter : BasePresenter {
        fun getPesanan(idMember: String)
    }

}