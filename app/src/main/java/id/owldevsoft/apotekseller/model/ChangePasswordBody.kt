package id.owldevsoft.apotekseller.model


import com.google.gson.annotations.SerializedName

data class ChangePasswordBody(
    @SerializedName("username")
    val username: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("new_password")
    val newPassword: String
)