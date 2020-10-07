package id.owldevsoft.apotekseller.feature.masterdata.subgolonganobat.delete

import id.owldevsoft.apotekseller.base.BasePresenter
import id.owldevsoft.apotekseller.base.BaseView

interface DeleteSubGolonganObatContract {

    interface View: BaseView<Presenter> {
        fun onDeleteError(code: Int, message: String)
        fun onDeleteSuccess(message: String)
        fun onProcess(boolean: Boolean)
    }

    interface Presenter : BasePresenter {
        fun deleteSubGolonganObat(idSubGolonganObat: String)
    }

}