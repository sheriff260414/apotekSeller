package id.owldevsoft.apotekseller.model

data class RegisterBody(
    var nama: String,
    var no_ktp: String,
    var email: String,
    var photosia: String,
    var photosipa: String,
    var photogedung: String,
    var no_hp: String,
    var jenis: String,
    var namaApotek: String
)