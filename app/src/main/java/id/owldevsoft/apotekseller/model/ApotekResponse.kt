package id.owldevsoft.apotekseller.model


import com.google.gson.annotations.SerializedName

data class ApotekResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
) {
    data class Data(
        @SerializedName("alamat")
        val alamat: String,
        @SerializedName("faktur_akhir")
        val fakturAkhir: String,
        @SerializedName("faktur_awal")
        val fakturAwal: String,
        @SerializedName("faktur_pakai")
        val fakturPakai: String,
        @SerializedName("flg_pkp")
        val flgPkp: String,
        @SerializedName("id_apotek")
        val idApotek: String,
        @SerializedName("id_kabupaten")
        val idKabupaten: String,
        @SerializedName("id_member")
        val idMember: String,
        @SerializedName("id_provinsi")
        val idProvinsi: String,
        @SerializedName("jam_buka")
        val jamBuka: String,
        @SerializedName("jam_tutup")
        val jamTutup: String,
        @SerializedName("kadaluwarsa")
        val kadaluwarsa: String,
        @SerializedName("latitude")
        val latitude: String,
        @SerializedName("longitude")
        val longitude: String,
        @SerializedName("nama_apotek")
        val namaApotek: String,
        @SerializedName("nama_apoteker")
        val namaApoteker: String,
        @SerializedName("no_sia")
        val noSia: String,
        @SerializedName("no_sipa")
        val noSipa: String,
        @SerializedName("no_telp")
        val noTelp: String,
        @SerializedName("npwp")
        val npwp: String,
        @SerializedName("platform")
        val platform: String,
        @SerializedName("post_image")
        val postImage: String,
        @SerializedName("tgl_saldo_awal")
        val tglSaldoAwal: String
    )
}