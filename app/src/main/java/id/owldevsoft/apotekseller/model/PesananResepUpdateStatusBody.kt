package id.owldevsoft.apotekseller.model


import com.google.gson.annotations.SerializedName

data class PesananResepUpdateStatusBody(
    @SerializedName("biaya_pengiriman")
    val biayaPengiriman: Int,
    @SerializedName("id_resep_mobile")
    val idResepMobile: Int,
    @SerializedName("status")
    val status: String,
    @SerializedName("total")
    val total: Int
)