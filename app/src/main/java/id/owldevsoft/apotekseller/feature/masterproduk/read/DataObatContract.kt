package id.owldevsoft.apotekseller.feature.masterproduk.read

import id.owldevsoft.apotekseller.base.BasePresenter
import id.owldevsoft.apotekseller.base.BaseView
import id.owldevsoft.apotekseller.model.ObatResponse

interface DataObatContract {

    interface View: BaseView<Presenter> {
        fun onObatError(code: Int, message: String)
        fun onObatSuccess(it: ArrayList<ObatResponse.Data>)
        fun onProcess(boolean: Boolean)
    }

    interface Presenter : BasePresenter {
        fun getObat(idMember: String)
    }

}