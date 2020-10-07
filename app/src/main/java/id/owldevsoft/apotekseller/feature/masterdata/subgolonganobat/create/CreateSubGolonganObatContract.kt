package id.owldevsoft.apotekseller.feature.masterdata.subgolonganobat.create

import id.owldevsoft.apotekseller.base.BasePresenter
import id.owldevsoft.apotekseller.base.BaseView
import id.owldevsoft.apotekseller.model.SubGolonganObatCreateBody

interface CreateSubGolonganObatContract {

    interface View: BaseView<Presenter> {
        fun onCreateError(code: Int, message: String)
        fun onCreateSuccess(message: String)
        fun onProcess(boolean: Boolean)
    }

    interface Presenter : BasePresenter {
        fun createSubGolonganObat(subGolonganObatCreateBody: SubGolonganObatCreateBody)
    }

}