package id.owldevsoft.apotekseller.model


import com.google.gson.annotations.SerializedName

data class SatuanUpdateBody(
    @SerializedName("id_satuan")
    val idSatuan: Int,
    @SerializedName("nama_satuan")
    val namaSatuan: String
)