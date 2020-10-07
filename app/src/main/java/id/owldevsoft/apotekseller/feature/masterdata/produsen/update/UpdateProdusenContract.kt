package id.owldevsoft.apotekseller.feature.masterdata.produsen.update

import id.owldevsoft.apotekseller.base.BasePresenter
import id.owldevsoft.apotekseller.base.BaseView
import id.owldevsoft.apotekseller.model.ProdusenUpdateBody

interface UpdateProdusenContract {

    interface View: BaseView<Presenter> {
        fun onUpdateError(code: Int, message: String)
        fun onUpdateSuccess(message: String)
        fun onProcess(boolean: Boolean)
    }

    interface Presenter : BasePresenter {
        fun updateProdusen(produsenUpdateBody: ProdusenUpdateBody)
    }

}