package id.owldevsoft.apotekseller.model


import com.google.gson.annotations.SerializedName

data class PesananObatDetailResponse(
    @SerializedName("data")
    val `data`: ArrayList<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
) {
    data class Data(
        @SerializedName("barcode_obat")
        val barcodeObat: Any,
        @SerializedName("catatan")
        val catatan: String,
        @SerializedName("created_by")
        val createdBy: String,
        @SerializedName("created_date")
        val createdDate: String,
        @SerializedName("deskripsi")
        val deskripsi: String,
        @SerializedName("dosis")
        val dosis: String,
        @SerializedName("edited_by")
        val editedBy: String,
        @SerializedName("edited_date")
        val editedDate: String,
        @SerializedName("harga")
        val harga: String,
        @SerializedName("id_detail_pesanan_mobile")
        val idDetailPesananMobile: String,
        @SerializedName("id_golongan")
        val idGolongan: String,
        @SerializedName("id_jenis_obat")
        val idJenisObat: String,
        @SerializedName("id_member")
        val idMember: String,
        @SerializedName("id_merk")
        val idMerk: String,
        @SerializedName("id_obat")
        val idObat: String,
        @SerializedName("id_pesanan_mobile")
        val idPesananMobile: String,
        @SerializedName("id_sub_golongan")
        val idSubGolongan: String,
        @SerializedName("indikasi")
        val indikasi: String,
        @SerializedName("jumlah")
        val jumlah: String,
        @SerializedName("kode_obat")
        val kodeObat: String,
        @SerializedName("komposisi")
        val komposisi: String,
        @SerializedName("nama_obat")
        val namaObat: String,
        @SerializedName("platform")
        val platform: String,
        @SerializedName("post_image")
        val postImage: String,
        @SerializedName("stok_max")
        val stokMax: String,
        @SerializedName("stok_min")
        val stokMin: String,
        @SerializedName("stok_total")
        val stokTotal: String,
        @SerializedName("tipe")
        val tipe: String,
        @SerializedName("total")
        val total: String
    )
}