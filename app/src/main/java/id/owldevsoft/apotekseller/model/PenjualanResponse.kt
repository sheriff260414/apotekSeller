package id.owldevsoft.apotekseller.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class PenjualanResponse(
    @SerializedName("data")
    val `data`: ArrayList<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
) {
    @Parcelize
    data class Data(
        @SerializedName("barcode_obat")
        val barcodeObat: String,
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
        @SerializedName("id_obat_mobile")
        val idObatMobile: String,
        @SerializedName("id_satuan")
        val idSatuan: String,
        @SerializedName("id_sub_golongan")
        val idSubGolongan: String,
        @SerializedName("indikasi")
        val indikasi: String,
        @SerializedName("kategori")
        val kategori: String,
        @SerializedName("kode_obat")
        val kodeObat: String,
        @SerializedName("komposisi")
        val komposisi: String,
        @SerializedName("nama_golongan")
        val namaGolongan: String,
        @SerializedName("nama_jenis")
        val namaJenis: String,
        @SerializedName("nama_merk")
        val namaMerk: String,
        @SerializedName("nama_obat")
        val namaObat: String,
        @SerializedName("nama_satuan")
        val namaSatuan: String,
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
        val tipe: String
    ) : Parcelable
}