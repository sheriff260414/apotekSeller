package id.owldevsoft.apotekseller.feature.pesananobat.detailpesanan

import id.owldevsoft.apotekseller.base.BasePresenter
import id.owldevsoft.apotekseller.base.BaseView
import id.owldevsoft.apotekseller.model.PesananObatDetailResponse
import id.owldevsoft.apotekseller.model.PesananObatResponse

interface DetailPesananObatContract {

    interface View: BaseView<Presenter> {
        fun onError(code: Int, message: String)
        fun onDetailSuccess(it: ArrayList<PesananObatDetailResponse.Data>)
        fun onProcess(boolean: Boolean)
    }

    interface Presenter : BasePresenter {
        fun getDetailPesanan(idPesanan: String)
    }

}