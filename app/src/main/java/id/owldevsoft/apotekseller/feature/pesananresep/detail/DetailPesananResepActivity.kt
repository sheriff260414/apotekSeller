package id.owldevsoft.apotekseller.feature.pesananresep.detail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import id.owldevsoft.apotekseller.R
import id.owldevsoft.apotekseller.model.PesananResepResponse
import kotlinx.android.synthetic.main.action_bar.*
import kotlinx.android.synthetic.main.activity_detail_pesanan_resep.*
import java.text.NumberFormat
import java.util.*

class DetailPesananResepActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_pesanan_resep)

        titlebar.text = "Detail Pesanan Resep"

        val extra = intent.getStringExtra("data")
        val data = Gson().fromJson(extra, PesananResepResponse.Data::class.java)

        tv_nomorpesanan_detailpesananresep.text = data.noPesanan
        tv_tglpesanan_detailpesananresep.text = data.dateCreated
        tv_alamatpesanan_detailpesananresep.text = data.alamat
        tv_catatanpesanan_detailpesananresep.text = data.catatan
        tv_tambahanbiayapesanan_detailpesananresep.text = "Rp " + NumberFormat.getNumberInstance(
            Locale.US
        ).format(data.biayaPengiriman.toInt())
        tv_totalpesanan_detailpesananresep.text =
            "Rp " + NumberFormat.getNumberInstance(Locale.US).format(data.total.toInt())
        tv_statuspesanan_detailpesananresep.text = data.status

        back.setOnClickListener { finish() }

    }

}