package id.owldevsoft.apotekseller.model


import com.google.gson.annotations.SerializedName

data class ProdusenCreateBody(
    @SerializedName("id_member")
    val idMember: Int,
    @SerializedName("nama_merk")
    val namaMerk: String
)