package id.owldevsoft.apotekseller.feature.masterdata.produsen.create

import id.owldevsoft.apotekseller.base.BasePresenter
import id.owldevsoft.apotekseller.base.BaseView
import id.owldevsoft.apotekseller.model.ProdusenCreateBody

interface CreateProdusenContract {

    interface View: BaseView<Presenter> {
        fun onCreateError(code: Int, message: String)
        fun onCreateSuccess(message: String)
        fun onProcess(boolean: Boolean)
    }

    interface Presenter : BasePresenter {
        fun createProdusen(produsenCreateBody: ProdusenCreateBody)
    }

}