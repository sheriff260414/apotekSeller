package id.owldevsoft.apotekseller.model


import com.google.gson.annotations.SerializedName

data class GolonganObatResponse(
    @SerializedName("data")
    val `data`: ArrayList<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
) {
    data class Data(
        @SerializedName("id_golongan")
        val idGolongan: String,
        @SerializedName("nama_golongan")
        val namaGolongan: String
    )
}