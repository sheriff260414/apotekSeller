package id.owldevsoft.apotekseller.feature.masterdata.produsen.delete

import id.owldevsoft.apotekseller.base.BasePresenter
import id.owldevsoft.apotekseller.base.BaseView

interface DeleteProdusenContract {

    interface View: BaseView<Presenter> {
        fun onDeleteError(code: Int, message: String)
        fun onDeleteSuccess(message: String)
        fun onProcess(boolean: Boolean)
    }

    interface Presenter : BasePresenter {
        fun deleteProdusen(idMerk: String)
    }

}