package id.owldevsoft.apotekseller.model

class DataObatBody(
    var nama_obat: String,
    var kode: String,
    var barcode: String,
    var id_jenis_obat: String,
    var id_golongan: String,
    var id_sub_golongan: String,
    var id_merk: String,
    var tipe: String,
    var indikasi: String,
    var photo: String,
    var deskripsi: String,
    var komposisi: String,
    var dosis: String,
    var stok: String,
    var id_member: String

) {
    class DataObatUpdateBody(
        var nama_obat: String,
        var kode: String,
        var barcode: String,
        var id_jenis_obat: String,
        var id_golongan: String,
        var id_sub_golongan: String,
        var id_merk: String,
        var tipe: String,
        var indikasi: String,
        var photo: String,
        var deskripsi: String,
        var komposisi: String,
        var dosis: String,
        var stok: String,
        var id_obat: String,
        var flg_photo: String

    )
}