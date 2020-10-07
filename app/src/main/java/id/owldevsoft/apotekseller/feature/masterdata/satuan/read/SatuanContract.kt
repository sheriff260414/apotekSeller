package id.owldevsoft.apotekseller.feature.masterdata.satuan.read

import id.owldevsoft.apotekseller.base.BasePresenter
import id.owldevsoft.apotekseller.base.BaseView
import id.owldevsoft.apotekseller.model.SatuanObatResponse

interface SatuanContract {

    interface View: BaseView<Presenter> {
        fun onSatuanError(code: Int, message: String)
        fun onSatuanSuccess(it: ArrayList<SatuanObatResponse.Data>)
        fun onProcess(boolean: Boolean)
    }

    interface Presenter : BasePresenter {
        fun getSatuan(idMember: String)
    }

}