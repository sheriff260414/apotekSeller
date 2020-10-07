package id.owldevsoft.apotekseller.model


import com.google.gson.annotations.SerializedName

data class SatuanObatResponse(
    @SerializedName("data")
    val `data`: ArrayList<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
) {
    data class Data(
        @SerializedName("id_member")
        val idMember: String,
        @SerializedName("id_satuan")
        val idSatuan: String,
        @SerializedName("nama_satuan")
        val namaSatuan: String
    )
}