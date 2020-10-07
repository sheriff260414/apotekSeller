package id.owldevsoft.apotekseller.feature.masterproduk.update

import id.owldevsoft.apotekseller.base.BasePresenter
import id.owldevsoft.apotekseller.base.BaseView
import id.owldevsoft.apotekseller.model.*

interface UpdateDataObatContract {

    interface View: BaseView<Presenter> {
        fun onJenisGolonganSuccess(it: ArrayList<JenisObatResponse.Data>)
        fun onSubGolonganSuccess(it: ArrayList<SubGolonganObatResponse.Data>)
        fun onMerkSuccess(it: ArrayList<ProdusenResponse.Data>)
        fun onGolonganSuccess(it: ArrayList<GolonganObatResponse.Data>)
        fun onUpdateObatSuccess(message: String)
        fun onError(code: Int, message: String)
        fun onProcess(boolean: Boolean)
    }

    interface Presenter : BasePresenter {
        fun getJenisGolonganObat(idMember: String)
        fun getSubGolongan(idMember: String)
        fun getProdusen(idMember: String)
        fun getGolongan()
        fun postUpdateObat(dataObatUpdateBody: DataObatBody.DataObatUpdateBody)
    }

}