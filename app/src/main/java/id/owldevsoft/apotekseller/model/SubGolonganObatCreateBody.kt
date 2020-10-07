package id.owldevsoft.apotekseller.model


import com.google.gson.annotations.SerializedName

data class SubGolonganObatCreateBody(
    @SerializedName("id_member")
    val idMember: Int,
    @SerializedName("nama_sub_golongan")
    val namaSubGolongan: String
)