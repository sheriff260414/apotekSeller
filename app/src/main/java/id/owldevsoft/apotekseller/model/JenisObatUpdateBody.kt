package id.owldevsoft.apotekseller.model


import com.google.gson.annotations.SerializedName

data class JenisObatUpdateBody(
    @SerializedName("id_jenis_obat")
    val idJenisObat: Int,
    @SerializedName("nama_jenis")
    val namaJenis: String
)