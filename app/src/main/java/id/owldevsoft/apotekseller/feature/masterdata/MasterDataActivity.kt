package id.owldevsoft.apotekseller.feature.masterdata

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import id.owldevsoft.apotekseller.R
import id.owldevsoft.apotekseller.feature.masterdata.jenisgolonganobat.read.JenisGolonganObatActivity
import id.owldevsoft.apotekseller.feature.masterdata.produsen.read.ProdusenActivity
import id.owldevsoft.apotekseller.feature.masterdata.satuan.read.SatuanActivity
import id.owldevsoft.apotekseller.feature.masterdata.subgolonganobat.read.SubGolonganObatActivity
import kotlinx.android.synthetic.main.action_bar.*
import kotlinx.android.synthetic.main.activity_master_data.*

class MasterDataActivity: AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_master_data)

        back.setOnClickListener(this)
        card_satuan.setOnClickListener(this)
        card_produsen.setOnClickListener(this)
        card_jenis_golongan_obat.setOnClickListener(this)
        card_sub_golongan_obat.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            back.id -> finish()
            card_satuan.id -> {
                val intent = Intent(this, SatuanActivity::class.java)
                startActivity(intent)
            }
            card_produsen.id -> {
                val intent = Intent(this, ProdusenActivity::class.java)
                startActivity(intent)
            }
            card_jenis_golongan_obat.id -> {
                val intent = Intent(this, JenisGolonganObatActivity::class.java)
                startActivity(intent)
            }
            card_sub_golongan_obat.id -> {
                val intent = Intent(this, SubGolonganObatActivity::class.java)
                startActivity(intent)
            }
        }
    }

}