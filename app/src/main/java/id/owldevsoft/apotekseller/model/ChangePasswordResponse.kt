package id.owldevsoft.apotekseller.model


import com.google.gson.annotations.SerializedName

data class ChangePasswordResponse(
    @SerializedName("data")
    val `data`: ArrayList<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
) {
    data class Data(
        @SerializedName("api_key")
        val apiKey: String,
        @SerializedName("created_by")
        val createdBy: String,
        @SerializedName("created_date")
        val createdDate: String,
        @SerializedName("edited_by")
        val editedBy: String,
        @SerializedName("edited_date")
        val editedDate: String,
        @SerializedName("id_members")
        val idMembers: String,
        @SerializedName("id_role")
        val idRole: String,
        @SerializedName("id_user")
        val idUser: String,
        @SerializedName("password")
        val password: String,
        @SerializedName("username")
        val username: String
    )
}