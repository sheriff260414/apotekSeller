package id.owldevsoft.apotekseller.feature.pesananobat.read

import id.owldevsoft.apotekseller.base.BasePresenter
import id.owldevsoft.apotekseller.base.BaseView
import id.owldevsoft.apotekseller.model.PesananObatResponse

interface PesananObatContract {

    interface View: BaseView<Presenter> {
        fun onError(code: Int, message: String)
        fun onPesananSuccess(it: ArrayList<PesananObatResponse.Data>)
        fun onProcess(boolean: Boolean)
    }

    interface Presenter : BasePresenter {
        fun getPesanan(idMember: String)
    }

}