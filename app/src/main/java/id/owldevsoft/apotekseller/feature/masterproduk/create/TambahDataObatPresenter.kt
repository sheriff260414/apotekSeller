package id.owldevsoft.apotekseller.feature.masterproduk.create

import android.content.Context
import androidx.core.net.toUri
import id.owldevsoft.apotekseller.model.DataObatBody
import id.owldevsoft.apotekseller.network.ApiProvider
import id.owldevsoft.apotekseller.network.Network
import id.owldevsoft.apotekseller.utils.ImageHelper
import id.owldevsoft.apotekseller.utils.PreferenceHelper
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

class TambahDataObatPresenter(val context: Context, var view: TambahDataObatContract.View?) :
    TambahDataObatContract.Presenter {

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

    override fun postObat(dataObatBody: DataObatBody) {
        val namaObat = dataObatBody.nama_obat.toRequestBody(MultipartBody.FORM)
        val kode = dataObatBody.kode.toRequestBody(MultipartBody.FORM)
        val barcode = dataObatBody.barcode.toRequestBody(MultipartBody.FORM)
        val idJenisObat = dataObatBody.id_jenis_obat.toRequestBody(MultipartBody.FORM)
        val idGolongan = dataObatBody.id_golongan.toRequestBody(MultipartBody.FORM)
        val idSubGolongan = dataObatBody.id_sub_golongan.toRequestBody(MultipartBody.FORM)
        val idMerk = dataObatBody.id_merk.toRequestBody(MultipartBody.FORM)
        val tipe = dataObatBody.tipe.toRequestBody(MultipartBody.FORM)
        val indikasi = dataObatBody.indikasi.toRequestBody(MultipartBody.FORM)
        val photo = ImageHelper.convertToBody("photo", dataObatBody.photo.toUri())
        val deskripsi = dataObatBody.deskripsi.toRequestBody(MultipartBody.FORM)
        val komposisi = dataObatBody.komposisi.toRequestBody(MultipartBody.FORM)
        val dosis = dataObatBody.dosis.toRequestBody(MultipartBody.FORM)
        val stok = dataObatBody.stok.toRequestBody(MultipartBody.FORM)
        val idMember = dataObatBody.id_member.toRequestBody(MultipartBody.FORM)

        Network.request(context,
            call = ApiProvider.create().postCreateObat(
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
                idMember
            ),
            success = {
                if (it.status == "success") {
                    view?.onCreateObatSuccess(it.message)
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