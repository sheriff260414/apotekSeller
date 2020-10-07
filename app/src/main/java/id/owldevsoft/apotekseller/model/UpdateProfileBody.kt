package id.owldevsoft.apotekseller.model

data class UpdateProfileBody (
    var id_apotek: String,
    var nama_apotek: String,
    var no_telp: String,
    var npwp: String,
    var no_sia: String,
    var nama_apoteker: String,
    var no_sipa: String,
    var jam_buka: String,
    var jam_tutup: String,
    var longitude: String,
    var latitude: String,
    var id_kabupaten: String,
    var photo: String? = null,
    var alamat: String,
    var flg_photo: String

)