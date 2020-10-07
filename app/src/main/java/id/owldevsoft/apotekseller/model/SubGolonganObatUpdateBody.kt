package id.owldevsoft.apotekseller.model


import com.google.gson.annotations.SerializedName

data class SubGolonganObatUpdateBody(
    @SerializedName("id_sub_golongan")
    val idSubGolongan: Int,
    @SerializedName("nama_sub_golongan")
    val namaSubGolongan: String
)