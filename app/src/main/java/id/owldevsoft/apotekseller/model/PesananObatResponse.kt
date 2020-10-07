package id.owldevsoft.apotekseller.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class PesananObatResponse(
    @SerializedName("data")
    val `data`: ArrayList<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
) {
    @Parcelize
    data class Data(
        @SerializedName("alamat")
        val alamat: String,
        @SerializedName("api_key")
        val apiKey: String,
        @SerializedName("biaya_pengiriman")
        val biayaPengiriman: String,
        @SerializedName("catatan")
        val catatan: String,
        @SerializedName("date_created")
        val dateCreated: String,
        @SerializedName("email")
        val email: String,
        @SerializedName("faktur_akhir")
        val fakturAkhir: String,
        @SerializedName("faktur_awal")
        val fakturAwal: String,
        @SerializedName("faktur_pakai")
        val fakturPakai: String,
        @SerializedName("flg_pesanan")
        val flgPesanan: String,
        @SerializedName("flg_pkp")
        val flgPkp: String,
        @SerializedName("gol_darah")
        val golDarah: String,
        @SerializedName("id_apotek")
        val idApotek: String,
        @SerializedName("id_kabupaten")
        val idKabupaten: String,
        @SerializedName("id_member")
        val idMember: String,
        @SerializedName("id_mobile")
        val idMobile: String,
        @SerializedName("id_pesanan_mobile")
        val idPesananMobile: String,
        @SerializedName("id_provinsi")
        val idProvinsi: String,
        @SerializedName("jam_buka")
        val jamBuka: String,
        @SerializedName("jam_tutup")
        val jamTutup: String,
        @SerializedName("jenis_kelamin")
        val jenisKelamin: String,
        @SerializedName("kadaluwarsa")
        val kadaluwarsa: String,
        @SerializedName("latitude")
        val latitude: String,
        @SerializedName("longitude")
        val longitude: String,
        @SerializedName("nama")
        val nama: String,
        @SerializedName("nama_apotek")
        val namaApotek: String,
        @SerializedName("nama_apoteker")
        val namaApoteker: String,
        @SerializedName("no_ktp")
        val noKtp: String,
        @SerializedName("no_pesanan")
        val noPesanan: String,
        @SerializedName("no_sia")
        val noSia: String,
        @SerializedName("no_sipa")
        val noSipa: String,
        @SerializedName("no_telp")
        val noTelp: String,
        @SerializedName("npwp")
        val npwp: String,
        @SerializedName("password")
        val password: String,
        @SerializedName("platform")
        val platform: String,
        @SerializedName("post_image")
        val postImage: String,
        @SerializedName("potongan_voucher")
        val potonganVoucher: String,
        @SerializedName("status")
        val status: String,
        @SerializedName("tgl_lahir")
        val tglLahir: String,
        @SerializedName("tgl_saldo_awal")
        val tglSaldoAwal: String,
        @SerializedName("total")
        val total: String,
        @SerializedName("umur")
        val umur: String,
        @SerializedName("username")
        val username: String,
        @SerializedName("voucher")
        val voucher: String
    ) : Parcelable
}