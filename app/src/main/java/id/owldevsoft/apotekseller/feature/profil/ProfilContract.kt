package id.owldevsoft.apotekseller.feature.profil

import id.owldevsoft.apotekseller.base.BasePresenter
import id.owldevsoft.apotekseller.base.BaseView
import id.owldevsoft.apotekseller.model.*

interface ProfilContract {

    interface View: BaseView<Presenter> {
        fun onApotekSuccess(apotekResponse: ApotekResponse)
        fun onProvinsiSuccess(daerahResponse: DaerahResponse)
        fun onKabupatenSuccess(daerahResponse: DaerahResponse)
        fun onUpdateProfileSuccess(message: String)
        fun onChangePasswordSuccess(it: ArrayList<ChangePasswordResponse.Data>)
        fun onError(code: Int, message: String)
        fun onProcess(boolean: Boolean)
        fun onProcessChangePass(boolean: Boolean)
    }
    interface Presenter : BasePresenter {
        fun getApotek(idApotek: String)
        fun getprovinsi(daerahBody: DaerahBody)
        fun getKabupaten(daerahBody: DaerahBody)
        fun updateProfile(updaeProfileBody: UpdateProfileBody)
        fun changePassword(changePasswordBody: ChangePasswordBody)
    }

}