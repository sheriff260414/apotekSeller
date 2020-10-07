package id.owldevsoft.apotekseller.model


import com.google.gson.annotations.SerializedName

data class PesananObatUpdateStatusBody(
    @SerializedName("biaya_pengiriman")
    val biayaPengiriman: Int,
    @SerializedName("id_pesanan_mobile")
    val idPesananMobile: Int,
    @SerializedName("status")
    val status: String,
    @SerializedName("total")
    val total: Int
)