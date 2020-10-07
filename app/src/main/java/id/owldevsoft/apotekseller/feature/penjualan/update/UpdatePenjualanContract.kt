package id.owldevsoft.apotekseller.feature.penjualan.update

import id.owldevsoft.apotekseller.base.BasePresenter
import id.owldevsoft.apotekseller.base.BaseView
import id.owldevsoft.apotekseller.model.AddPenjualanBody
import id.owldevsoft.apotekseller.model.JenisObatUpdateBody

interface UpdatePenjualanContract {

    interface View: BaseView<Presenter> {
        fun onUpdateError(code: Int, message: String)
        fun onUpdateSuccess(message: String)
        fun onProcess(boolean: Boolean)
    }

    interface Presenter : BasePresenter {
        fun updatePenjualan(updatePenjualanBody: AddPenjualanBody.updatePenjualanBody)
    }

}