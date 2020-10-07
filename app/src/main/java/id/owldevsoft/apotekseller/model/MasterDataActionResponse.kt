package id.owldevsoft.apotekseller.model


import com.google.gson.annotations.SerializedName

data class MasterDataActionResponse(
    @SerializedName("data")
    val `data`: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
) {
    data class ActionDelete(
        @SerializedName("data")
        val `data`: String,
        @SerializedName("message")
        val message: String,
        @SerializedName("status")
        val status: String
    )
}