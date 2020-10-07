package id.owldevsoft.apotekseller.feature.pesananresep.updatestatus

import id.owldevsoft.apotekseller.base.BasePresenter
import id.owldevsoft.apotekseller.base.BaseView
import id.owldevsoft.apotekseller.model.PesananObatUpdateStatusBody
import id.owldevsoft.apotekseller.model.PesananResepUpdateStatusBody

interface UpdateStatusPesananResepContract {

    interface View: BaseView<Presenter> {
        fun onError(code: Int, message: String)
        fun onUpdateSuccess(message: String)
        fun onProcess(boolean: Boolean)
    }

    interface Presenter : BasePresenter {
        fun updateStatusPesananResep(pesananResepUpdateStatusBody: PesananResepUpdateStatusBody)
    }

}