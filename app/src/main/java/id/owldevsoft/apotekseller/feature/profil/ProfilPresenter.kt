package id.owldevsoft.apotekseller.feature.profil

import android.content.Context
import androidx.core.net.toUri
import id.owldevsoft.apotekseller.model.ChangePasswordBody
import id.owldevsoft.apotekseller.model.DaerahBody
import id.owldevsoft.apotekseller.model.UpdateProfileBody
import id.owldevsoft.apotekseller.network.ApiProvider
import id.owldevsoft.apotekseller.network.Network
import id.owldevsoft.apotekseller.utils.ImageHelper
import id.owldevsoft.apotekseller.utils.PreferenceHelper
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

class ProfilPresenter(val context: Context, var view: ProfilContract.View?) :
    ProfilContract.Presenter {

    var apiKey: String? = null
    private val preferenceHelper = PreferenceHelper(context)

    init {
        view?.presenter = this
        apiKey = preferenceHelper.getUser()!!.data.user[0].apiKey
    }

    override fun start() {

    }

    override fun destroy() {
        view = null
    }

    override fun getApotek(idApotek: String) {
        Network.request(context,
            call = ApiProvider.create()
                .getApotekProfile(apiKey!!, idApotek),
            success = {
                if (it.status == "success") {
                    view?.onApotekSuccess(it)
                } else {
                    view?.onError(0, it.message)
                }
            },
            error = fun(_: Throwable, code: Int, message: String) {
                view?.onError(code, message)
            },
            doOnSubscribe = {
                view?.onProcess(true)
            },
            doOnTerminate = {
                view?.onProcess(false)
            })
    }

    override fun getprovinsi(daerahBody: DaerahBody) {
        Network.request(context,
            call = ApiProvider.create()
                .getDaerah(apiKey!!, daerahBody),
            success = {
                if (it.status == "success") {
                    view?.onProvinsiSuccess(it)
                } else {
                    view?.onError(0, it.message)
                }
            },
            error = fun(_: Throwable, code: Int, message: String) {
                view?.onError(code, message)
            },
            doOnSubscribe = {
            },
            doOnTerminate = {
            })
    }

    override fun getKabupaten(daerahBody: DaerahBody) {
        Network.request(context,
            call = ApiProvider.create()
                .getDaerah(apiKey!!, daerahBody),
            success = {
                if (it.status == "success") {
                    view?.onKabupatenSuccess(it)
                } else {
                    view?.onError(0, it.message)
                }
            },
            error = fun(_: Throwable, code: Int, message: String) {
                view?.onError(code, message)
            },
            doOnSubscribe = {
            },
            doOnTerminate = {
            })
    }

    override fun updateProfile(updaeProfileBody: UpdateProfileBody) {
        val idApotek = updaeProfileBody.id_apotek.toRequestBody(MultipartBody.FORM)
        val namaApotek = updaeProfileBody.nama_apotek.toRequestBody(MultipartBody.FORM)
        val noTelp = updaeProfileBody.no_telp.toRequestBody(MultipartBody.FORM)
        val npwp = updaeProfileBody.npwp.toRequestBody(MultipartBody.FORM)
        val noSia = updaeProfileBody.no_sia.toRequestBody(MultipartBody.FORM)
        val namaApoteker = updaeProfileBody.nama_apoteker.toRequestBody(MultipartBody.FORM)
        val noSipa = updaeProfileBody.no_sipa.toRequestBody(MultipartBody.FORM)
        val jamBuka = updaeProfileBody.jam_buka.toRequestBody(MultipartBody.FORM)
        val jamTutup = updaeProfileBody.jam_tutup.toRequestBody(MultipartBody.FORM)
        val longitude = updaeProfileBody.longitude.toRequestBody(MultipartBody.FORM)
        val latitude = updaeProfileBody.latitude.toRequestBody(MultipartBody.FORM)
        val idKabupaten = updaeProfileBody.id_kabupaten.toRequestBody(MultipartBody.FORM)
        val photo = ImageHelper.convertToBody("photo", updaeProfileBody.photo?.toUri())
        val alamat = updaeProfileBody.alamat.toRequestBody(MultipartBody.FORM)
        val flgPhoto = updaeProfileBody.flg_photo.toRequestBody(MultipartBody.FORM)

        Network.request(context,
            call = ApiProvider.create().postUpdateProfile(
                apiKey!!,
                idApotek,
                namaApotek,
                noTelp,
                npwp,
                noSia,
                namaApoteker,
                noSipa,
                jamBuka,
                jamTutup,
                longitude,
                latitude,
                idKabupaten,
                photo,
                alamat,
                flgPhoto
            ),
            success = {
                if (it.status == "success") {
                    view?.onUpdateProfileSuccess(it.message)
                } else {
                    view?.onError(0, it.message)
                }
            },
            error = fun(t: Throwable, code: Int, message: String) {
                view?.onError(code, message)
            },
            doOnSubscribe = {
                view?.onProcess(true)
            },
            doOnTerminate = {
                view?.onProcess(false)
            })

    }

    override fun changePassword(changePasswordBody: ChangePasswordBody) {
        Network.request(context,
            call = ApiProvider.create().changePassword(apiKey!!, changePasswordBody),
            success = {
                view?.onChangePasswordSuccess(it.data)
            },
            error = fun(t: Throwable, code: Int, message: String) {
                view?.onError(code, message)
            },
            doOnSubscribe = {
                view?.onProcessChangePass(true)
            }, doOnTerminate = {
                view?.onProcessChangePass(false)
            })
    }

}