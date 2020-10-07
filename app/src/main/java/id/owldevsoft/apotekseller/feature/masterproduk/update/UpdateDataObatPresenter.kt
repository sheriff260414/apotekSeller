package id.owldevsoft.apotekseller.feature.masterproduk.update

import android.content.Context
import androidx.core.net.toUri
import id.owldevsoft.apotekseller.model.DataObatBody
import id.owldevsoft.apotekseller.network.ApiProvider
import id.owldevsoft.apotekseller.network.Network
import id.owldevsoft.apotekseller.utils.ImageHelper
import id.owldevsoft.apotekseller.utils.PreferenceHelper
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

class UpdateDataObatPresenter(val context: Context, var view: UpdateDataObatContract.View?) :
    UpdateDataObatContract.Presenter {

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

    override fun getJenisGolonganObat(idMember: String) {
        Network.request(context,
            call = ApiProvider.create()
                .getDataJenisObat(apiKey!!, idMember),
            success = {
                if (it.status == "success") {
                    if (it.data.isNotEmpty()) {
                        view?.onJenisGolonganSuccess(it.data)
                    }
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

    override fun getSubGolongan(idMember: String) {
        Network.request(context, call = ApiProvider.create().getDataSubGolonganObat(apiKey!!, idMember),
            success = {
                if (it.status == "success") {
                    if (it.data.isNotEmpty()){
                        view?.onSubGolonganSuccess(it.data)
                    }
                } else {
                    view?.onError(0, it.message)
                }
            },
            error = fun (_: Throwable, code: Int, message: String){
                view?.onError(code, message)
            },
            doOnSubscribe = {

            },
            doOnTerminate = {

            })
    }

    override fun getProdusen(idMember: String) {
        Network.request(context, call = ApiProvider.create().getDataProdusen(apiKey!!, idMember),
            success = {
                if (it.status == "success") {
                    if (it.data.isNotEmpty()) {
                        view?.onMerkSuccess(it.data)
                    }
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

    override fun getGolongan() {
        Network.request(context, call = ApiProvider.create().getGolongan(apiKey!!),
            success = {
                if (it.status == "success") {
                    if (it.data.isNotEmpty()) {
                        view?.onGolonganSuccess(it.data)
                    }
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

    override fun postUpdateObat(dataObatUpdateBody: DataObatBody.DataObatUpdateBody) {
        val namaObat = dataObatUpdateBody.nama_obat.toRequestBody(MultipartBody.FORM)
        val kode = dataObatUpdateBody.kode.toRequestBody(MultipartBody.FORM)
        val barcode = dataObatUpdateBody.barcode.toRequestBody(MultipartBody.FORM)
        val idJenisObat = dataObatUpdateBody.id_jenis_obat.toRequestBody(MultipartBody.FORM)
        val idGolongan = dataObatUpdateBody.id_golongan.toRequestBody(MultipartBody.FORM)
        val idSubGolongan = dataObatUpdateBody.id_sub_golongan.toRequestBody(MultipartBody.FORM)
        val idMerk = dataObatUpdateBody.id_merk.toRequestBody(MultipartBody.FORM)
        val tipe = dataObatUpdateBody.tipe.toRequestBody(MultipartBody.FORM)
        val indikasi = dataObatUpdateBody.indikasi.toRequestBody(MultipartBody.FORM)
        val photo = ImageHelper.convertToBody("photo", dataObatUpdateBody.photo.toUri())
        val deskripsi = dataObatUpdateBody.deskripsi.toRequestBody(MultipartBody.FORM)
        val komposisi = dataObatUpdateBody.komposisi.toRequestBody(MultipartBody.FORM)
        val dosis = dataObatUpdateBody.dosis.toRequestBody(MultipartBody.FORM)
        val stok = dataObatUpdateBody.stok.toRequestBody(MultipartBody.FORM)
        val idObat = dataObatUpdateBody.id_obat.toRequestBody(MultipartBody.FORM)
        val flgPhoto = dataObatUpdateBody.flg_photo.toRequestBody(MultipartBody.FORM)

        Network.request(context,
            call = ApiProvider.create().postUpdateObat(
                apiKey!!,
                namaObat,
                kode,
                barcode,
                idJenisObat,
                idGolongan,
                idSubGolongan,
                idMerk,
                tipe,
                indikasi,
                photo,
                deskripsi,
                komposisi,
                dosis,
                stok,
                idObat,
                flgPhoto
            ),
            success = {
                if (it.status == "success") {
                    view?.onUpdateObatSuccess(it.message)
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

}