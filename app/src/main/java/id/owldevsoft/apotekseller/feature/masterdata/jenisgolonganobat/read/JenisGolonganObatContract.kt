package id.owldevsoft.apotekseller.feature.masterdata.jenisgolonganobat.read

import id.owldevsoft.apotekseller.base.BasePresenter
import id.owldevsoft.apotekseller.base.BaseView
import id.owldevsoft.apotekseller.model.JenisObatResponse

interface JenisGolonganObatContract {

    interface View: BaseView<Presenter> {
        fun onJenisGolonganError(code: Int, message: String)
        fun onJenisGolonganSuccess(it: ArrayList<JenisObatResponse.Data>)
        fun onProcess(boolean: Boolean)
    }

    interface Presenter : BasePresenter {
        fun getJenisGolonganObat(idMember: String)
    }

}