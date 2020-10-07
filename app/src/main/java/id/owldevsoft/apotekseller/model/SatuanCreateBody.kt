package id.owldevsoft.apotekseller.model


import com.google.gson.annotations.SerializedName

data class SatuanCreateBody(
    @SerializedName("id_member")
    val idMember: Int,
    @SerializedName("nama_satuan")
    val namaSatuan: String
)