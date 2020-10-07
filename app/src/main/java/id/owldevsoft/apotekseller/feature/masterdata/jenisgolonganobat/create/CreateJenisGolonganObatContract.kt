package id.owldevsoft.apotekseller.feature.masterdata.jenisgolonganobat.create

import id.owldevsoft.apotekseller.base.BasePresenter
import id.owldevsoft.apotekseller.base.BaseView
import id.owldevsoft.apotekseller.model.JenisObatCreateBody

interface CreateJenisGolonganObatContract {

    interface View: BaseView<Presenter> {
        fun onCreateError(code: Int, message: String)
        fun onCreateSuccess(message: String)
        fun onProcess(boolean: Boolean)
    }

    interface Presenter : BasePresenter {
        fun createJenisGolonganObat(jenisObatCreateBody: JenisObatCreateBody)
    }

}