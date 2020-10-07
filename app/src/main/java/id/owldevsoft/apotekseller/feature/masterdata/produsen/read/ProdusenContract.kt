package id.owldevsoft.apotekseller.feature.masterdata.produsen.read

import id.owldevsoft.apotekseller.base.BasePresenter
import id.owldevsoft.apotekseller.base.BaseView
import id.owldevsoft.apotekseller.model.ProdusenResponse

interface ProdusenContract {

    interface View: BaseView<Presenter> {
        fun onMerkError(code: Int, message: String)
        fun onMerkSuccess(it: ArrayList<ProdusenResponse.Data>)
        fun onProcess(boolean: Boolean)
    }

    interface Presenter : BasePresenter {
        fun getProdusen(idMember: String)
    }

}