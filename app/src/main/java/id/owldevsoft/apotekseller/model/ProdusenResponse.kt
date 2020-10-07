package id.owldevsoft.apotekseller.model


import com.google.gson.annotations.SerializedName

data class ProdusenResponse(
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
        @SerializedName("id_merk")
        val idMerk: String,
        @SerializedName("nama_merk")
        val namaMerk: String
    )
}