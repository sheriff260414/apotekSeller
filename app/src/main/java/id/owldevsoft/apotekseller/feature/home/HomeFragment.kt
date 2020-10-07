package id.owldevsoft.apotekseller.feature.home

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.Gson
import id.owldevsoft.apotekseller.R
import id.owldevsoft.apotekseller.adapter.BannerAdapter
import id.owldevsoft.apotekseller.adapter.BannerLoading
import id.owldevsoft.apotekseller.feature.banner.DetailBannerActivity
import id.owldevsoft.apotekseller.feature.masterdata.MasterDataActivity
import id.owldevsoft.apotekseller.feature.masterproduk.read.DataObatActivity
import id.owldevsoft.apotekseller.feature.member.MemberActivity
import id.owldevsoft.apotekseller.feature.penjualan.read.PenjualanActivity
import id.owldevsoft.apotekseller.feature.pesananobat.read.PesananObatActivity
import id.owldevsoft.apotekseller.feature.pesananresep.read.PesananResepActivity
import id.owldevsoft.apotekseller.model.BannerResponse
import id.owldevsoft.apotekseller.model.PesananObatResponse
import id.owldevsoft.apotekseller.model.PesananResepResponse
import id.owldevsoft.apotekseller.utils.BubbleService
import id.owldevsoft.apotekseller.utils.DialogHelper
import id.owldevsoft.apotekseller.utils.PreferenceHelper
import kotlinx.android.synthetic.main.fragment_home.*
import ss.com.bannerslider.Slider

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment(), View.OnClickListener, HomeContract.View, SwipeRefreshLayout.OnRefreshListener {

    override lateinit var presenter: HomeContract.Presenter
    private var preferenceHelper: PreferenceHelper? = null
    private var listPesanan: ArrayList<PesananObatResponse.Data>? = null
    private var listResep: ArrayList<PesananResepResponse.Data>? = null

    private val PERMISION_REQUEST_CODE = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        HomePresenter(requireContext(), this)
        preferenceHelper = PreferenceHelper(requireContext())

        btnMasterData.setOnClickListener(this)
        btnMasterProduk.setOnClickListener(this)
        btnPenjualan.setOnClickListener(this)
        btnPesananObat.setOnClickListener(this)
        btnPesananResep.setOnClickListener(this)
        iv_member.setOnClickListener(this)

        presenter.getBanner()
        presenter.getPesanan(preferenceHelper?.getUser()!!.data.member[0].idMembers)
        presenter.getResep(preferenceHelper?.getUser()!!.data.member[0].idMembers)

        refresh_home.apply {
            setOnRefreshListener(this@HomeFragment)
        }

        permisionBubble()

    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            btnMasterData.id -> {
                val intent = Intent(context, MasterDataActivity::class.java)
                startActivity(intent)
            }
            btnMasterProduk.id -> {
                val intent = Intent(context, DataObatActivity::class.java)
                startActivity(intent)
            }
            btnPenjualan.id -> {
                val intent = Intent(context, PenjualanActivity::class.java)
                startActivity(intent)
            }
            btnPesananObat.id -> {
                val intent = Intent(context, PesananObatActivity::class.java)
                startActivity(intent)
            }
            iv_member.id -> {
                val intent = Intent(context, MemberActivity::class.java)
                startActivity(intent)
            }
            btnPesananResep.id -> {
                val intent = Intent(context, PesananResepActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onError(code: Int, message: String) {
        activity?.let {
            refresh_home.isRefreshing = false
            DialogHelper.showWarnDialog(
                it,
                message,
                getString(R.string.ans_mes_ok),
                false,
                object : DialogHelper.Positive {
                    override fun positiveButton(dialog: DialogInterface, id: Int) {
                        dialog.dismiss()
                    }
                })
        }

    }

    override fun onPesananSuccess(it: ArrayList<PesananObatResponse.Data>) {
        listPesanan = ArrayList()
        it.forEachIndexed{ index, data ->
            if (data.status != "Selesai"){
                listPesanan?.add(data)
            }
        }
        if (listPesanan.isNullOrEmpty()){
            tv_jml_pesanan_obat.text = "0"
        } else{
            tv_jml_pesanan_obat.text = listPesanan?.size.toString()
        }
    }

    override fun onResepSuccess(it: ArrayList<PesananResepResponse.Data>) {
        listResep = ArrayList()
        it.forEachIndexed{ index, data ->
            if (data.status != "Selesai"){
                listResep?.add(data)
            }
        }
        if (listResep.isNullOrEmpty()){
            tv_jml_pesanan_resep.text = "0"
        } else{
            tv_jml_pesanan_resep.text = listResep?.size.toString()
        }
    }

    override fun onBannerSuccess(bannerResponse: BannerResponse) {
        refresh_home.isRefreshing = false
        setupBanner(bannerResponse.data)
    }

    override fun onProcess(boolean: Boolean) {

    }

    private fun setupBanner(data: List<BannerResponse.Data>) {
        eventBanner.visibility = View.VISIBLE
        Slider.init(BannerLoading())
        val adapterBanner = BannerAdapter(data)
        eventBanner.setAdapter(adapterBanner)
        eventBanner.setOnSlideClickListener {
            startActivity(
                Intent(context, DetailBannerActivity::class.java)
                    .putExtra("data", Gson().toJson(data[it]))
            )
        }
        if (data.size > 1) {
            eventBanner.setLoopSlides(true)
            eventBanner.setInterval(6000)
        }
    }

    override fun onRefresh() {
        presenter.getBanner()
        presenter.getPesanan(preferenceHelper?.getUser()!!.data.member[0].idMembers)
        presenter.getResep(preferenceHelper?.getUser()!!.data.member[0].idMembers)
    }

    private fun permisionBubble() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(requireContext())) {
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + activity?.packageName)
            )
            startActivityForResult(intent, PERMISION_REQUEST_CODE)
        } else {
            showWA()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PERMISION_REQUEST_CODE){
            if (resultCode == Activity.RESULT_OK){
                showWA()
            }
        }
    }

    private fun showWA() {
        activity?.startService(Intent(requireContext(), BubbleService::class.java))
    }

    override fun onResume() {
        super.onResume()
        showWA()
    }

    override fun onPause() {
        super.onPause()
        activity?.stopService(Intent(requireContext(), BubbleService::class.java))
    }

}