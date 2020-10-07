package id.owldevsoft.apotekseller.feature.masterproduk.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import id.owldevsoft.apotekseller.R
import id.owldevsoft.apotekseller.model.ObatResponse
import kotlinx.android.synthetic.main.action_bar.*
import kotlinx.android.synthetic.main.activity_detail_obat.*

class DetailObatActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_obat)

        titlebar.text = "Detail Obat"

        val extra = intent.getStringExtra("data")
        val data = Gson().fromJson(extra, ObatResponse.Data::class.java)

        tv_nama_obat_detail.text = data.namaObat
        tv_kode_obat_detail.text = data.kodeObat
        tv_barkode_obat_detail.text = data.barcodeObat
        tv_jenis_obat_detail.text = data.namaJenis
        tv_golongan_obat_detail.text = data.namaGolongan
        tv_sub_obat_detail.text = data.namaSubGolongan
        tv_merk_obat_detail.text = data.namaMerk
        tv_tipe_obat_detail.text = data.tipe
        tv_indikasi_obat_detail.text = data.indikasi
        tv_deskripsi_obat_detail.text = data.deskripsi
        tv_komposisi_obat_detail.text = data.komposisi
        tv_dosis_obat_detail.text = data.dosis
        tv_stok_total_obat_detail.text = data.stokTotal
//        tv_stok_min_obat_detail.text = data.stokMin
//        tv_stok_max_obat_detail.text = data.stokMax

        back.setOnClickListener {
            finish()
        }

    }

}