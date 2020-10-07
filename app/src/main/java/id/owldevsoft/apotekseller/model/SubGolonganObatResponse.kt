package id.owldevsoft.apotekseller.model


import com.google.gson.annotations.SerializedName

data class SubGolonganObatResponse(
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
        @SerializedName("id_sub_golongan")
        val idSubGolongan: String,
        @SerializedName("nama_sub_golongan")
        val namaSubGolongan: String
    )
}