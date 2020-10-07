package id.owldevsoft.apotekseller.feature.banner

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import id.owldevsoft.apotekseller.BuildConfig
import id.owldevsoft.apotekseller.R
import id.owldevsoft.apotekseller.model.BannerResponse
import kotlinx.android.synthetic.main.action_bar.*
import kotlinx.android.synthetic.main.activity_detail_banner.*

class DetailBannerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_banner)

        val extra = intent.getStringExtra("data")
        val data = Gson().fromJson(extra, BannerResponse.Data::class.java)
        back.setOnClickListener { onBackPressed() }
        titlebar.text = "Detail"
        Picasso.get().load(BuildConfig.BASE_URL_BANNER+data.postImage).placeholder(R.drawable.placeholder).into(imgBanner)
        tvTitle.text = data.judul
        tvDest.text = data.detail

    }
}