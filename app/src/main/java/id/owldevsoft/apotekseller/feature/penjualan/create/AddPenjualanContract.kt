package id.owldevsoft.apotekseller.feature.penjualan.create

import id.owldevsoft.apotekseller.base.BasePresenter
import id.owldevsoft.apotekseller.base.BaseView
import id.owldevsoft.apotekseller.model.AddPenjualanBody
import id.owldevsoft.apotekseller.model.ObatResponse
import id.owldevsoft.apotekseller.model.Response
import id.owldevsoft.apotekseller.model.SatuanObatResponse
import retrofit2.Call

interface AddPenjualanContract {

    interface View: BaseView<Presenter> {
        fun onAddPenjualanSuccess(response: Response.ResponseUpdate)
        fun onObatSuccess(it: ArrayList<ObatResponse.Data>)
        fun onSatuanSuccess(it: ArrayList<SatuanObatResponse.Data>)
//        fun onCancel(call: Call<*>)
        fun onError(code: Int, message: String)
        fun onProcess(boolean: Boolean)
        fun onProcessGet(boolean: Boolean)
    }
    interface Presenter : BasePresenter {
        fun addPenjualan(addPenjualanBody: AddPenjualanBody)
        fun getObat(idMember: String)
        fun getSatuan(idMember: String)
    }

}