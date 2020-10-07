package id.owldevsoft.apotekseller.feature.pesananobat.updatestatus

import id.owldevsoft.apotekseller.base.BasePresenter
import id.owldevsoft.apotekseller.base.BaseView
import id.owldevsoft.apotekseller.model.PesananObatUpdateStatusBody

interface UpdateStatusPesananObatContract {

    interface View: BaseView<Presenter> {
        fun onError(code: Int, message: String)
        fun onUpdateSuccess(message: String)
        fun onProcess(boolean: Boolean)
    }

    interface Presenter : BasePresenter {
        fun updateStatusPesananObat(pesananObatUpdateStatusBody: PesananObatUpdateStatusBody)
    }

}