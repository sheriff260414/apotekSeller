package id.owldevsoft.apotekseller.model


import com.google.gson.annotations.SerializedName

data class BannerResponse(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
) {
    data class Data(
        @SerializedName("created_by")
        val createdBy: String,
        @SerializedName("created_date")
        val createdDate: String,
        @SerializedName("detail")
        val detail: String,
        @SerializedName("id_banner")
        val idBanner: String,
        @SerializedName("judul")
        val judul: String,
        @SerializedName("post_image")
        val postImage: String
    )
}