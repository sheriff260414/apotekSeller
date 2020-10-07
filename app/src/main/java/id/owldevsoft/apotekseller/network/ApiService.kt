package id.owldevsoft.apotekseller.network

import id.owldevsoft.apotekseller.model.*
import kotlinx.coroutines.Deferred
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {

    //login
    @POST("loginseller")
    fun postLogin(
        @Body loginBody: LoginBody
    ): Deferred<LoginResponse>

    //register
    @Multipart
    @POST("registerseller")
    fun postRegister(
        @Part("nama") nama: RequestBody,
        @Part("no_ktp") no_ktp: RequestBody,
        @Part("email") email: RequestBody,
        @Part photosia: MultipartBody.Part?,
        @Part photosipa: MultipartBody.Part?,
        @Part photogedung: MultipartBody.Part?,
        @Part("no_hp") no_hp: RequestBody,
        @Part("jenis") jenis: RequestBody,
        @Part("nama_apotek") nama_apotek: RequestBody
    ): Deferred<Response>

    //banner
    @GET("banner")
    fun getBanner(
        @Header("Key") api_key: String
    ):Deferred<BannerResponse>

    //profile apotek
    @GET("apotekseller/{id_apotek}")
    fun getApotekProfile(
        @Header("Key") api_key: String,
        @Path("id_apotek") id_apotek: String
    ): Deferred<ApotekResponse>

    //provinsi & kabupaten
    @POST("daerah")
    fun getDaerah(
        @Header("Key") api_key: String,
        @Body daerahBody: DaerahBody
    ):Deferred<DaerahResponse>

    //update apotek
    @Multipart
    @POST("apotekseller")
    fun postUpdateProfile(
        @Header("Key") api_key: String,
        @Part("id_apotek") id_apotik: RequestBody,
        @Part("nama_apotek") nama_apotik: RequestBody,
        @Part("no_telp") no_telp: RequestBody,
        @Part("npwp") npwp: RequestBody,
        @Part("no_sia") no_sia: RequestBody,
        @Part("nama_apoteker") nama_apoteker: RequestBody,
        @Part("no_sipa") no_sipa: RequestBody,
        @Part("jam_buka") jam_buka: RequestBody,
        @Part("jam_tutup") jam_tutup: RequestBody,
        @Part("longitude") longitude: RequestBody,
        @Part("latitude") latitude: RequestBody,
        @Part("id_kabupaten") id_kabupaten: RequestBody,
        @Part photo: MultipartBody.Part?,
        @Part("alamat") alamat: RequestBody,
        @Part("flg_photo") flg_photo: RequestBody
    ): Deferred<Response.ResponseUpdate>

    //change password
    @PUT("changepassword")
    fun changePassword(
        @Header("Key") api_key: String,
        @Body changePasswordBody: ChangePasswordBody
    ): Deferred<ChangePasswordResponse>

    //master data satuan
    @GET("satuanobat/{id_member}")
    fun getDataSatuan(
        @Header("Key") api_key: String,
        @Path("id_member") id_member: String
    ): Deferred<SatuanObatResponse>

    @PUT("satuanobat")
    fun updateSatuan(
        @Header("Key") api_key: String,
        @Body satuanUpdateBody: SatuanUpdateBody
    ): Deferred<MasterDataActionResponse>

    @POST("satuanobat")
    fun createSatuan(
        @Header("Key") api_key: String,
        @Body satuanCreateBody: SatuanCreateBody
    ): Deferred<MasterDataActionResponse>

    @DELETE("satuanobat/{id_satuan}")
    fun deleteSatuan(
        @Header("Key") api_key: String,
        @Path("id_satuan") id_satuan: String
    ): Deferred<MasterDataActionResponse.ActionDelete>

    //master data produsen
    @GET("merk/{id_member}")
    fun getDataProdusen(
        @Header("Key") api_key: String,
        @Path("id_member") id_member: String
    ): Deferred<ProdusenResponse>

    @PUT("merk")
    fun updateProdusen(
        @Header("Key") api_key: String,
        @Body produsenUpdateBody: ProdusenUpdateBody
    ): Deferred<MasterDataActionResponse>

    @POST("merk")
    fun createProdusen(
        @Header("Key") api_key: String,
        @Body produsenCreateBody: ProdusenCreateBody
    ): Deferred<MasterDataActionResponse>

    @DELETE("merk/{id_merk}")
    fun deleteProdusen(
        @Header("Key") api_key: String,
        @Path("id_merk") id_merk: String
    ): Deferred<MasterDataActionResponse.ActionDelete>

    //master data sub golongan obat
    @GET("subgolongan/{id_member}")
    fun getDataSubGolonganObat(
        @Header("Key") api_key: String,
        @Path("id_member") id_member: String
    ): Deferred<SubGolonganObatResponse>

    @PUT("subgolongan")
    fun updateSubGolonganObat(
        @Header("Key") api_key: String,
        @Body subGolonganObatUpdateBody: SubGolonganObatUpdateBody
    ): Deferred<MasterDataActionResponse>

    @POST("subgolongan")
    fun createSubGolonganObat(
        @Header("Key") api_key: String,
        @Body subGolonganObatCreateBody: SubGolonganObatCreateBody
    ): Deferred<MasterDataActionResponse>

    @DELETE("subgolongan/{id_sub_golongan}")
    fun deleteSubGolonganObat(
        @Header("Key") api_key: String,
        @Path("id_sub_golongan") id_sub_golongan: String
    ): Deferred<MasterDataActionResponse.ActionDelete>

    //master data jenis golongan obat
    @GET("jenisobat/{id_member}")
    fun getDataJenisObat(
        @Header("Key") api_key: String,
        @Path("id_member") id_member: String
    ): Deferred<JenisObatResponse>

    @PUT("jenisobat")
    fun updateJenisObat(
        @Header("Key") api_key: String,
        @Body jenisObatUpdateBody: JenisObatUpdateBody
    ): Deferred<MasterDataActionResponse>

    @POST("jenisobat")
    fun createJenisObat(
        @Header("Key") api_key: String,
        @Body jenisObatCreateBody: JenisObatCreateBody
    ): Deferred<MasterDataActionResponse>

    @DELETE("jenisobat/{id_jenis_obat}")
    fun deleteJenisObat(
        @Header("Key") api_key: String,
        @Path("id_jenis_obat") id_jenis_obat: String
    ): Deferred<MasterDataActionResponse.ActionDelete>

    //master produk
    @GET("obat/{id_member}")
    fun getObat(
        @Header("Key") api_key: String,
        @Path("id_member") id_member: String
    ): Deferred<ObatResponse>

    @DELETE("obat/{id_obat}")
    fun deleteObat(
        @Header("Key") api_key: String,
        @Path("id_obat") id_obat: String
    ): Deferred<MasterDataActionResponse.ActionDelete>

    @GET("golongan")
    fun getGolongan(
        @Header("Key") api_key: String
    ): Deferred<GolonganObatResponse>

    @Multipart
    @POST("obat")
    fun postCreateObat(
        @Header("Key") api_key: String,
        @Part("nama_obat") nama_obat: RequestBody,
        @Part("kode") kode: RequestBody,
        @Part("barcode") barcode: RequestBody,
        @Part("id_jenis_obat") id_jenis_obat: RequestBody,
        @Part("id_golongan") id_golongan: RequestBody,
        @Part("id_sub_golongan") id_sub_golongan: RequestBody,
        @Part("id_merk") id_merk: RequestBody,
        @Part("tipe") tipe: RequestBody,
        @Part("indikasi") indikasi: RequestBody,
        @Part photo: MultipartBody.Part?,
        @Part("deskripsi") deskripsi: RequestBody,
        @Part("komposisi") komposisi: RequestBody,
        @Part("dosis") dosis: RequestBody,
        @Part("stok") stok: RequestBody,
        @Part("id_member") id_member: RequestBody
    ): Deferred<MasterDataActionResponse>

    @Multipart
    @POST("updateobat")
    fun postUpdateObat(
        @Header("Key") api_key: String,
        @Part("nama_obat") nama_obat: RequestBody,
        @Part("kode") kode: RequestBody,
        @Part("barcode") barcode: RequestBody,
        @Part("id_jenis_obat") id_jenis_obat: RequestBody,
        @Part("id_golongan") id_golongan: RequestBody,
        @Part("id_sub_golongan") id_sub_golongan: RequestBody,
        @Part("id_merk") id_merk: RequestBody,
        @Part("tipe") tipe: RequestBody,
        @Part("indikasi") indikasi: RequestBody,
        @Part photo: MultipartBody.Part?,
        @Part("deskripsi") deskripsi: RequestBody,
        @Part("komposisi") komposisi: RequestBody,
        @Part("dosis") dosis: RequestBody,
        @Part("stok") stok: RequestBody,
        @Part("id_obat") id_obat: RequestBody,
        @Part("flg_photo") flg_photo: RequestBody
    ): Deferred<MasterDataActionResponse>

    //penjualan
    @POST("penjualan")
    fun createPenjualan(
        @Header("Key") api_key: String,
        @Body addPenjualanBody: AddPenjualanBody
    ): Deferred<Response.ResponseUpdate>

    @GET("penjualan/{id_member}")
    fun getPenjualan(
        @Header("Key") api_key: String,
        @Path("id_member") id_member: String
    ): Deferred<PenjualanResponse>

    @PUT("penjualan")
    fun updatePenjualan(
        @Header("Key") api_key: String,
        @Body updatePenjualanBody: AddPenjualanBody.updatePenjualanBody
    ): Deferred<MasterDataActionResponse>

    @DELETE("penjualan/{id_obat_mobile}")
    fun deletePenjualan(
        @Header("Key") api_key: String,
        @Path("id_obat_mobile") id_obat_mobile: String
    ): Deferred<MasterDataActionResponse.ActionDelete>

    //Pesanan Obat
    @GET("pemesanan/{id_member}")
    fun getPesananObat(
        @Header("Key") api_key: String,
        @Path("id_member") id_member: String
    ): Deferred<PesananObatResponse>

    @PUT("pemesanan")
    fun updateStatusPesananObat(
        @Header("Key") api_key: String,
        @Body pesananObatUpdateStatusBody: PesananObatUpdateStatusBody
    ): Deferred<MasterDataActionResponse>

    @GET("detailpemesanan/{id_pesanan}")
    fun getDetailPesananObat(
        @Header("Key") api_key: String,
        @Path("id_pesanan") id_pesanan: String
    ): Deferred<PesananObatDetailResponse>

    //Pesanan Resep
    @GET("resep/{id_member}")
    fun getPesananResep(
        @Header("Key") api_key: String,
        @Path("id_member") id_member: String
    ): Deferred<PesananResepResponse>

    @PUT("resep")
    fun updateStatusPesananResep(
        @Header("Key") api_key: String,
        @Body pesananResepUpdateStatusBody: PesananResepUpdateStatusBody
    ): Deferred<MasterDataActionResponse>

}