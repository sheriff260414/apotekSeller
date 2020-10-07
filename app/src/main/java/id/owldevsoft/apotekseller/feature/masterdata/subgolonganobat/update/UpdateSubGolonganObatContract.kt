package id.owldevsoft.apotekseller.feature.masterdata.subgolonganobat.update

import id.owldevsoft.apotekseller.base.BasePresenter
import id.owldevsoft.apotekseller.base.BaseView
import id.owldevsoft.apotekseller.model.SubGolonganObatUpdateBody

interface UpdateSubGolonganObatContract {

    interface View: BaseView<Presenter> {
        fun onUpdateError(code: Int, message: String)
        fun onUpdateSuccess(message: String)
        fun onProcess(boolean: Boolean)
    }

    interface Presenter : BasePresenter {
        fun updateSatuan(subGolonganObatUpdateBody: SubGolonganObatUpdateBody)
    }

}