package id.owldevsoft.apotekseller.feature.pesananresep.read

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import id.owldevsoft.apotekseller.R
import id.owldevsoft.apotekseller.adapter.PesananResepAdapter
import id.owldevsoft.apotekseller.feature.pesananobat.detailpemesan.PemesanPesananObatFragment
import id.owldevsoft.apotekseller.feature.pesananresep.detail.DetailPesananResepActivity
import id.owldevsoft.apotekseller.feature.pesananresep.detailpemesan.PemesanPesananResepFragment
import id.owldevsoft.apotekseller.feature.pesananresep.detailpesanan.DetailPesananResepFragment
import id.owldevsoft.apotekseller.feature.pesananresep.updatestatus.UpdateStatusPesananResepFragment
import id.owldevsoft.apotekseller.model.PesananResepResponse
import id.owldevsoft.apotekseller.utils.DialogHelper
import id.owldevsoft.apotekseller.utils.PreferenceHelper
import kotlinx.android.synthetic.main.action_bar.*
import kotlinx.android.synthetic.main.activity_pesanan_resep.*
import java.util.*
import kotlin.collections.ArrayList

class PesananResepActivity : AppCompatActivity(),
    PesananResepContract.View,
    UpdateStatusPesananResepFragment.OnCloseFragmentUpdateStatusPesananResep {

    override lateinit var presenter: PesananResepContract.Presenter
    private var preferenceHelper: PreferenceHelper? = null

    private var loading: AlertDialog? = null
    private var pesananResepAdapter: PesananResepAdapter? = null

    private var listPesanan: ArrayList<PesananResepResponse.Data>? = null
    private var searchListPesanan: ArrayList<PesananResepResponse.Data>? = null

    private lateinit var updateStatusPesananResepFragment: UpdateStatusPesananResepFragment
    private lateinit var pemesanPesananResepFragment: PemesanPesananResepFragment
    private lateinit var detailpesananResepFragment: DetailPesananResepFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pesanan_resep)

        PesananResepPresenter(
            this,
            this
        )
        preferenceHelper = PreferenceHelper(this)

        titlebar.text = "Data Pesanan Resep"
        loading = DialogHelper.showDialogLoading(this)
        presenter.getPesanan(preferenceHelper?.getUser()!!.data.member[0].idMembers)

        back.setOnClickListener {
            finish()
        }

        et_search_pesananresep.addTextChangedListener(onSearchChange())
        iv_clear_pesanan_resep.setOnClickListener {
            et_search_pesananresep.setText("")
        }

    }

    private fun onSearchChange(): TextWatcher? {
        return object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                searchListPesanan = ArrayList()
                if (p0?.isEmpty()!!) {
                    searchListPesanan = listPesanan
                    iv_clear_pesanan_resep.visibility = View.GONE
                } else {
                    iv_clear_pesanan_resep.visibility = View.VISIBLE
                    searchListPesanan = filterPesanan(p0.toString())
                }

                val clickActionPesanan = object : PesananResepAdapter.OnClickActionPesanan {

                    override fun onClickDetailOrder(position: PesananResepResponse.Data) {
                        val intent = Intent(
                            this@PesananResepActivity,
                            DetailPesananResepActivity::class.java
                        )
                        intent.putExtra("data", Gson().toJson(position))
                        startActivity(intent)
                    }

                    override fun onClickChangeStatus(position: PesananResepResponse.Data) {
                        updateStatusPesananResepFragment = UpdateStatusPesananResepFragment()
                        val bundle = Bundle()
                        bundle.putParcelable(
                            UpdateStatusPesananResepFragment.DATA_PESANAN_RESEP,
                            position
                        )
                        updateStatusPesananResepFragment.arguments = bundle
                        updateStatusPesananResepFragment.setStyle(
                            DialogFragment.STYLE_NORMAL,
                            R.style.DialogFragmentTheme
                        )
                        updateStatusPesananResepFragment.show(
                            supportFragmentManager.beginTransaction(),
                            "Update Pesanan Resep"
                        )
                    }

                    override fun onClickDetailPesanan(position: PesananResepResponse.Data) {
                        detailpesananResepFragment = DetailPesananResepFragment()
                        val bundle = Bundle()
                        bundle.putParcelable(
                            PemesanPesananResepFragment.DATA_PESANAN_RESEP,
                            position
                        )
                        detailpesananResepFragment.arguments = bundle
                        detailpesananResepFragment.setStyle(
                            DialogFragment.STYLE_NORMAL,
                            R.style.DialogFragmentTheme
                        )
                        detailpesananResepFragment.show(
                            supportFragmentManager.beginTransaction(),
                            "Detail Pesanan Resep"
                        )
                    }

                    override fun onClickDetailPemesan(position: PesananResepResponse.Data) {
                        pemesanPesananResepFragment = PemesanPesananResepFragment()
                        val bundle = Bundle()
                        bundle.putParcelable(
                            PemesanPesananResepFragment.DATA_PESANAN_RESEP,
                            position
                        )
                        pemesanPesananResepFragment.arguments = bundle
                        pemesanPesananResepFragment.setStyle(
                            DialogFragment.STYLE_NORMAL,
                            R.style.DialogFragmentTheme
                        )
                        pemesanPesananResepFragment.show(
                            supportFragmentManager.beginTransaction(),
                            "Pemesan Pesanan Resep"
                        )
                    }

                }

                pesananResepAdapter =
                    PesananResepAdapter(searchListPesanan, baseContext, clickActionPesanan)
                rv_pesanan_resep.adapter = pesananResepAdapter
                rv_pesanan_resep.layoutManager =
                    LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL, false)

            }

        }
    }

    private fun filterPesanan(search: String): ArrayList<PesananResepResponse.Data>? {
        val result: ArrayList<PesananResepResponse.Data> = ArrayList()
        for (i in listPesanan?.indices!!) {
            if (listPesanan!![i].noPesanan.toLowerCase(Locale.ROOT).contains(
                    search.toLowerCase(
                        Locale.ROOT
                    )
                )
            ) {
                result.add(listPesanan!![i])
            }
        }
        return result
    }

    override fun onError(code: Int, message: String) {
        DialogHelper.showWarnDialog(
            this,
            message,
            getString(R.string.ans_mes_ok),
            false,
            object : DialogHelper.Positive {
                override fun positiveButton(dialog: DialogInterface, id: Int) {
                    dialog.dismiss()
                }
            })
    }

    override fun onPesananSuccess(it: ArrayList<PesananResepResponse.Data>) {
        et_search_pesananresep.setText("")
        this.listPesanan = it
        val clickActionPesanan = object : PesananResepAdapter.OnClickActionPesanan {
            override fun onClickDetailOrder(position: PesananResepResponse.Data) {
                val intent =
                    Intent(this@PesananResepActivity, DetailPesananResepActivity::class.java)
                intent.putExtra("data", Gson().toJson(position))
                startActivity(intent)
            }

            override fun onClickChangeStatus(position: PesananResepResponse.Data) {
                updateStatusPesananResepFragment = UpdateStatusPesananResepFragment()
                val bundle = Bundle()
                bundle.putParcelable(
                    UpdateStatusPesananResepFragment.DATA_PESANAN_RESEP,
                    position
                )
                updateStatusPesananResepFragment.arguments = bundle
                updateStatusPesananResepFragment.setStyle(
                    DialogFragment.STYLE_NORMAL,
                    R.style.DialogFragmentTheme
                )
                updateStatusPesananResepFragment.show(
                    supportFragmentManager.beginTransaction(),
                    "Update Pesanan Resep"
                )
            }

            override fun onClickDetailPesanan(position: PesananResepResponse.Data) {
                detailpesananResepFragment = DetailPesananResepFragment()
                val bundle = Bundle()
                bundle.putParcelable(
                    PemesanPesananResepFragment.DATA_PESANAN_RESEP,
                    position
                )
                detailpesananResepFragment.arguments = bundle
                detailpesananResepFragment.setStyle(
                    DialogFragment.STYLE_NORMAL,
                    R.style.DialogFragmentTheme
                )
                detailpesananResepFragment.show(
                    supportFragmentManager.beginTransaction(),
                    "Detail Pesanan Resep"
                )
            }

            override fun onClickDetailPemesan(position: PesananResepResponse.Data) {
                pemesanPesananResepFragment = PemesanPesananResepFragment()
                val bundle = Bundle()
                bundle.putParcelable(
                    PemesanPesananResepFragment.DATA_PESANAN_RESEP,
                    position
                )
                pemesanPesananResepFragment.arguments = bundle
                pemesanPesananResepFragment.setStyle(
                    DialogFragment.STYLE_NORMAL,
                    R.style.DialogFragmentTheme
                )
                pemesanPesananResepFragment.show(
                    supportFragmentManager.beginTransaction(),
                    "Pemesan Pesanan Resep"
                )
            }

        }
        pesananResepAdapter = PesananResepAdapter(it, this, clickActionPesanan)
        rv_pesanan_resep.adapter = pesananResepAdapter
        rv_pesanan_resep.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    override fun onProcess(boolean: Boolean) {
        if (boolean) {
            loading?.show()
        } else {
            loading?.dismiss()
        }
    }

    override fun onCloseFragmentUpdateStatusPesananResep(b: Boolean) {
        presenter.getPesanan(preferenceHelper?.getUser()!!.data.member[0].idMembers)
    }

}