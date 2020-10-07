package id.owldevsoft.apotekseller.feature.home

import id.owldevsoft.apotekseller.base.BasePresenter
import id.owldevsoft.apotekseller.base.BaseView
import id.owldevsoft.apotekseller.model.BannerResponse
import id.owldevsoft.apotekseller.model.PesananObatResponse
import id.owldevsoft.apotekseller.model.PesananResepResponse

interface HomeContract {

    interface View: BaseView<Presenter>{
        fun onError(code: Int, message: String)
        fun onPesananSuccess(it: ArrayList<PesananObatResponse.Data>)
        fun onResepSuccess(it: ArrayList<PesananResepResponse.Data>)
        fun onBannerSuccess(bannerResponse: BannerResponse)
        fun onProcess(boolean: Boolean)
    }

    interface Presenter : BasePresenter{
        fun getBanner()
        fun getPesanan(idMember: String)
        fun getResep(idMember: String)
    }

}