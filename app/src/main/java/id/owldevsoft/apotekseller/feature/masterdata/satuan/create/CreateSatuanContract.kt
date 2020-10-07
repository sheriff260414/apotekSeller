package id.owldevsoft.apotekseller.feature.masterdata.satuan.create

import id.owldevsoft.apotekseller.base.BasePresenter
import id.owldevsoft.apotekseller.base.BaseView
import id.owldevsoft.apotekseller.model.SatuanCreateBody

interface CreateSatuanContract {

    interface View: BaseView<Presenter> {
        fun onCreateError(code: Int, message: String)
        fun onCreateSuccess(message: String)
        fun onProcess(boolean: Boolean)
    }

    interface Presenter : BasePresenter {
        fun createSatuan(satuanCreateBody: SatuanCreateBody)
    }

}