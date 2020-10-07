package id.owldevsoft.apotekseller.feature.masterdata.subgolonganobat.read

import id.owldevsoft.apotekseller.base.BasePresenter
import id.owldevsoft.apotekseller.base.BaseView
import id.owldevsoft.apotekseller.model.SubGolonganObatResponse

interface SubGolonganObatContract {

    interface View: BaseView<Presenter> {
        fun onSubGolonganError(code: Int, message: String)
        fun onSubGolonganSuccess(it: ArrayList<SubGolonganObatResponse.Data>)
        fun onProcess(boolean: Boolean)
    }

    interface Presenter : BasePresenter {
        fun getSubGolongan(idMember: String)
    }

}