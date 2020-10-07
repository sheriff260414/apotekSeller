package id.owldevsoft.apotekseller.model


import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
) {
    data class Data(
        @SerializedName("apotek")
        val apotek: ArrayList<Apotek>,
        @SerializedName("member")
        val member: ArrayList<Member>,
        @SerializedName("user")
        val user: ArrayList<User>
    ) {
        data class Apotek(
            @SerializedName("alamat")
            val alamat: String,
            @SerializedName("faktur_akhir")
            val fakturAkhir: String,
            @SerializedName("faktur_awal")
            val fakturAwal: String,
            @SerializedName("faktur_pakai")
            val fakturPakai: String,
            @SerializedName("flg_pkp")
            val flgPkp: String,
            @SerializedName("id_apotek")
            val idApotek: String,
            @SerializedName("id_kabupaten")
            val idKabupaten: String,
            @SerializedName("id_member")
            val idMember: String,
            @SerializedName("id_provinsi")
            val idProvinsi: String,
            @SerializedName("kadaluwarsa")
            val kadaluwarsa: String,
            @SerializedName("kota")
            val kota: String,
            @SerializedName("latitude")
            val latitude: String,
            @SerializedName("longitude")
            val longitude: String,
            @SerializedName("nama_apotek")
            val namaApotek: String,
            @SerializedName("nama_apoteker")
            val namaApoteker: String,
            @SerializedName("no_sia")
            val noSia: String,
            @SerializedName("no_sipa")
            val noSipa: String,
            @SerializedName("no_telp")
            val noTelp: String,
            @SerializedName("npwp")
            val npwp: String,
            @SerializedName("post_image")
            val postImage: String,
            @SerializedName("provinsi")
            val provinsi: String,
            @SerializedName("tgl_saldo_awal")
            val tglSaldoAwal: String
        )

        data class Member(
            @SerializedName("created_by")
            val createdBy: String,
            @SerializedName("created_date")
            val createdDate: String,
            @SerializedName("edited_by")
            val editedBy: String,
            @SerializedName("edited_date")
            val editedDate: String,
            @SerializedName("email")
            val email: String,
            @SerializedName("full_name")
            val fullName: String,
            @SerializedName("id_members")
            val idMembers: String,
            @SerializedName("id_user")
            val idUser: String,
            @SerializedName("members_end_date")
            val membersEndDate: String,
            @SerializedName("members_start_date")
            val membersStartDate: String,
            @SerializedName("members_status")
            val membersStatus: String,
            @SerializedName("members_type")
            val membersType: String,
            @SerializedName("phone_number")
            val phoneNumber: String
        )

        data class User(
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
}