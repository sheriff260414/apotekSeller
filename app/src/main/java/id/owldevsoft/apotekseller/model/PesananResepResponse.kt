package id.owldevsoft.apotekseller.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class PesananResepResponse(
    @SerializedName("data")
    val `data`: ArrayList<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
) {
    @Parcelize
    data class Data(
        @SerializedName("alamat")
        val alamat: String,
        @SerializedName("api_key")
        val apiKey: String,
        @SerializedName("biaya_pengiriman")
        val biayaPengiriman: String,
        @SerializedName("catatan")
        val catatan: String,
        @SerializedName("date_created")
        val dateCreated: String,
        @SerializedName("email")
        val email: String,
        @SerializedName("flg_pesanan")
        val flgPesanan: String,
        @SerializedName("gol_darah")
        val golDarah: String,
        @SerializedName("id_apotek")
        val idApotek: String,
        @SerializedName("id_member")
        val idMember: String,
        @SerializedName("id_mobile")
        val idMobile: String,
        @SerializedName("id_resep_mobile")
        val idResepMobile: String,
        @SerializedName("jenis_kelamin")
        val jenisKelamin: String,
        @SerializedName("latitude")
        val latitude: String,
        @SerializedName("longitude")
        val longitude: String,
        @SerializedName("nama")
        val nama: String,
        @SerializedName("no_ktp")
        val noKtp: String,
        @SerializedName("no_pesanan")
        val noPesanan: String,
        @SerializedName("no_telp")
        val noTelp: String,
        @SerializedName("password")
        val password: String,
        @SerializedName("post_image")
        val postImage: String,
        @SerializedName("status")
        val status: String,
        @SerializedName("tgl_lahir")
        val tglLahir: String,
        @SerializedName("total")
        val total: String,
        @SerializedName("umur")
        val umur: String,
        @SerializedName("username")
        val username: String
    ) : Parcelable
}