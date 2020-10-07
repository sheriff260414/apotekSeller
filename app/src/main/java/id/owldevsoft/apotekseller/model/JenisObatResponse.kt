package id.owldevsoft.apotekseller.model


import com.google.gson.annotations.SerializedName

data class JenisObatResponse(
    @SerializedName("data")
    val `data`: ArrayList<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
) {
    data class Data(
        @SerializedName("id_jenis_obat")
        val idJenisObat: String,
        @SerializedName("id_member")
        val idMember: String,
        @SerializedName("nama_jenis")
        val namaJenis: String
    )
}