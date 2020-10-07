package id.owldevsoft.apotekseller.model


import com.google.gson.annotations.SerializedName

data class JenisObatCreateBody(
    @SerializedName("id_member")
    val idMember: Int,
    @SerializedName("nama_jenis")
    val namaJenis: String
)