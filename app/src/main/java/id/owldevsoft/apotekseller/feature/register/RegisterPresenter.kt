package id.owldevsoft.apotekseller.feature.register


import android.content.Context
import androidx.core.net.toUri
import id.owldevsoft.apotekseller.model.RegisterBody
import id.owldevsoft.apotekseller.network.ApiProvider
import id.owldevsoft.apotekseller.network.Network
import id.owldevsoft.apotekseller.utils.ImageHelper
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

class RegisterPresenter(val context: Context, var view: RegisterContract.View?) :
    RegisterContract.Presenter {

    init {
        view?.presenter = this
    }

    override fun start() {

    }

    override fun destroy() {
        view = null
    }

    override fun doRegister(registerBody: RegisterBody) {
        val nama = registerBody.nama.toRequestBody(MultipartBody.FORM)
        val noKTP = registerBody.no_ktp.toRequestBody(MultipartBody.FORM)
        val email = registerBody.email.toRequestBody(MultipartBody.FORM)
        val photoSia = ImageHelper.convertToBody("photosia", registerBody.photosia.toUri())
        val photoSipa = ImageHelper.convertToBody("photosipa", registerBody.photosipa.toUri())
        val photoGedung = ImageHelper.convertToBody("photogedung", registerBody.photogedung.toUri())
        val noHP = registerBody.no_hp.toRequestBody(MultipartBody.FORM)
        val jenis = registerBody.jenis.toRequestBody(MultipartBody.FORM)
        val namaApotek = registerBody.namaApotek.toRequestBody(MultipartBody.FORM)

        Network.request(context,
            call = ApiProvider.create().postRegister(
                nama,
                noKTP,
                email,
                photoSia,
                photoSipa,
                photoGedung,
                noHP,
                jenis,
                namaApotek
            ),
            success = {
                if (it.status == "success") {
                    view?.onRegisterSuccess(it)
                } else {
                    view?.onRegisterError(0, it.message)
                }
            },
            error = fun(t: Throwable, code: Int, message: String) {
                view?.onRegisterError(code, message)
            },
            doOnSubscribe = {
                view?.onProcess(true)
            },
            doOnTerminate = {
                view?.onProcess(false)
            })
    }

}