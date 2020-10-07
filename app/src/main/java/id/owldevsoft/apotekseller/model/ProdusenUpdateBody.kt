package id.owldevsoft.apotekseller.model


import com.google.gson.annotations.SerializedName

data class ProdusenUpdateBody(
    @SerializedName("id_merk")
    val idMerk: Int,
    @SerializedName("nama_merk")
    val namaMerk: String
)