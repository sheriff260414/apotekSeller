package id.owldevsoft.apotekseller.feature.pesananobat.detail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import id.owldevsoft.apotekseller.R
import id.owldevsoft.apotekseller.model.PesananObatResponse
import kotlinx.android.synthetic.main.action_bar.*
import kotlinx.android.synthetic.main.activity_detail_pesanan_obat.*
import java.text.NumberFormat
import java.util.*

class DetailPesananObatActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_pesanan_obat)

        titlebar.text = "Detail Pesanan Obat"

        val extra = intent.getStringExtra("data")
        val data = Gson().fromJson(extra, PesananObatResponse.Data::class.java)

        tv_nomorpesanan_detailpesananobat.text = data.noPesanan
        tv_tglpesanan_detailpesananobat.text = data.dateCreated
        tv_alamatpesanan_detailpesananobat.text = data.alamat
        tv_catatanpesanan_detailpesananobat.text = data.catatan
        tv_tambahanbiayapesanan_detailpesananobat.text = "Rp " + NumberFormat.getNumberInstance(
            Locale.US
        ).format(data.biayaPengiriman.toInt())
        tv_voucherpesanan_detailpesananobat.text = data.voucher
        tv_diskonvoucherpesanan_detailpesananobat.text = "Rp " + NumberFormat.getNumberInstance(
            Locale.US
        ).format(data.potonganVoucher.toInt())
        tv_totalpesanan_detailpesananobat.text =
            "Rp " + NumberFormat.getNumberInstance(Locale.US).format(data.total.toInt())
        tv_statuspesanan_detailpesananobat.text = data.status

        back.setOnClickListener { finish() }

    }

}