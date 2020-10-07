package id.owldevsoft.apotekseller.feature.penjualan.read

import id.owldevsoft.apotekseller.base.BasePresenter
import id.owldevsoft.apotekseller.base.BaseView
import id.owldevsoft.apotekseller.model.PenjualanResponse

interface PenjualanContract {

    interface View: BaseView<Presenter> {
        fun onPenjualanError(code: Int, message: String)
        fun onPenjualanSuccess(it: ArrayList<PenjualanResponse.Data>)
        fun onProcess(boolean: Boolean)
    }

    interface Presenter : BasePresenter {
        fun getPenjualan(idMember: String)
    }

}