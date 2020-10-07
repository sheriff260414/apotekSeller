package id.owldevsoft.apotekseller.model


import com.google.gson.annotations.SerializedName

data class DaerahResponse(
    @SerializedName("data")
    val `data`: ArrayList<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
) {
    data class Data(
        @SerializedName("id_kab")
        val idKab: String,
        @SerializedName("id_prov")
        val idProv: String,
        @SerializedName("nama")
        val nama: String
    )
}