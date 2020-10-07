package id.owldevsoft.apotekseller.feature.masterdata.jenisgolonganobat.delete

import id.owldevsoft.apotekseller.base.BasePresenter
import id.owldevsoft.apotekseller.base.BaseView

interface DeleteJenisGolonganObatContract {

    interface View: BaseView<Presenter> {
        fun onDeleteError(code: Int, message: String)
        fun onDeleteSuccess(message: String)
        fun onProcess(boolean: Boolean)
    }

    interface Presenter : BasePresenter {
        fun deleteJenisGolonganObat(idJenisGolonganObat: String)
    }

}