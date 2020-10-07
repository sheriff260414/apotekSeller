package id.owldevsoft.apotekseller.model


import com.google.gson.annotations.SerializedName

data class AddPenjualanBody(
    @SerializedName("harga")
    val harga: Int,
    @SerializedName("id_member")
    val idMember: Int,
    @SerializedName("id_obat")
    val idObat: Int,
    @SerializedName("id_satuan")
    val idSatuan: Int,
    @SerializedName("kategori")
    val kategori: String
) {
    data class updatePenjualanBody(
        @SerializedName("harga")
        val harga: Int,
        @SerializedName("id_obat_mobile")
        val idObatMobile: Int,
        @SerializedName("id_obat")
        val idObat: Int,
        @SerializedName("id_satuan")
        val idSatuan: Int,
        @SerializedName("kategori")
        val kategori: String
    )
}