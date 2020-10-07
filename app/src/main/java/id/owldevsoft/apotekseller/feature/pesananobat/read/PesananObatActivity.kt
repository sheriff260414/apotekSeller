package id.owldevsoft.apotekseller.feature.pesananobat.read

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
import id.owldevsoft.apotekseller.adapter.PesananObatAdapter
import id.owldevsoft.apotekseller.feature.masterdata.satuan.delete.DeleteSatuanFragment
import id.owldevsoft.apotekseller.feature.pesananobat.detail.DetailPesananObatActivity
import id.owldevsoft.apotekseller.feature.pesananobat.detailpemesan.PemesanPesananObatFragment
import id.owldevsoft.apotekseller.feature.pesananobat.detailpesanan.DetailPesananObatFragment
import id.owldevsoft.apotekseller.feature.pesananobat.updatestatus.UpdateStatusPesananObatFragment
import id.owldevsoft.apotekseller.model.PesananObatResponse
import id.owldevsoft.apotekseller.utils.DialogHelper
import id.owldevsoft.apotekseller.utils.PreferenceHelper
import kotlinx.android.synthetic.main.action_bar.*
import kotlinx.android.synthetic.main.activity_pesanan_obat.*
import java.util.*
import kotlin.collections.ArrayList

class PesananObatActivity : AppCompatActivity(),
    PesananObatContract.View,
    UpdateStatusPesananObatFragment.OnCloseFragmentUpdateStatusPesananObat {

    override lateinit var presenter: PesananObatContract.Presenter
    private var preferenceHelper: PreferenceHelper? = null

    private var loading: AlertDialog? = null
    private var pesananObatAdapter: PesananObatAdapter? = null

    private var listPesanan: ArrayList<PesananObatResponse.Data>? = null
    private var searchListPesanan: ArrayList<PesananObatResponse.Data>? = null

    private lateinit var updateStatusPesananObatFragment: UpdateStatusPesananObatFragment
    private lateinit var detailPesananObatFragment: DetailPesananObatFragment
    private lateinit var pemesanPesananObatFragment: PemesanPesananObatFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pesanan_obat)

        PesananObatPresenter(
            this,
            this
        )
        preferenceHelper = PreferenceHelper(this)

        titlebar.text = "Data Pesanan Obat"
        loading = DialogHelper.showDialogLoading(this)
        presenter.getPesanan(preferenceHelper?.getUser()!!.data.member[0].idMembers)

        back.setOnClickListener {
            finish()
        }

        et_search_pesananobat.addTextChangedListener(onSearchChange())
        iv_clear_pesanan_obat.setOnClickListener {
            et_search_pesananobat.setText("")
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
                    iv_clear_pesanan_obat.visibility = View.GONE
                } else {
                    iv_clear_pesanan_obat.visibility = View.VISIBLE
                    searchListPesanan = filterPesanan(p0.toString())
                }

                val clickActionPesanan = object : PesananObatAdapter.OnClickActionPesanan {
                    override fun onClickDetailOrder(position: PesananObatResponse.Data) {
                        val intent =
                            Intent(this@PesananObatActivity, DetailPesananObatActivity::class.java)
                        intent.putExtra("data", Gson().toJson(position))
                        startActivity(intent)
                    }

                    override fun onClickChangeStatus(position: PesananObatResponse.Data) {
                        updateStatusPesananObatFragment = UpdateStatusPesananObatFragment()
                        val bundle = Bundle()
                        bundle.putParcelable(
                            UpdateStatusPesananObatFragment.DATA_PESANAN_OBAT,
                            position
                        )
                        updateStatusPesananObatFragment.arguments = bundle
                        updateStatusPesananObatFragment.setStyle(
                            DialogFragment.STYLE_NORMAL,
                            R.style.DialogFragmentTheme
                        )
                        updateStatusPesananObatFragment.show(
                            supportFragmentManager.beginTransaction(),
                            "Update Pesanan Obat"
                        )
                    }

                    override fun onClickDetailPesanan(position: PesananObatResponse.Data) {
                        detailPesananObatFragment = DetailPesananObatFragment()
                        val bundle = Bundle()
                        bundle.putString(DetailPesananObatFragment.ID_PESANAN_MOBILE, position.idPesananMobile)
                        detailPesananObatFragment.arguments = bundle
                        detailPesananObatFragment.setStyle(
                            DialogFragment.STYLE_NORMAL,
                            R.style.DialogFragmentTheme
                        )
                        detailPesananObatFragment.show(
                            supportFragmentManager.beginTransaction(),
                            "Detail Pesanan"
                        )
                    }

                    override fun onClickDetailPemesan(position: PesananObatResponse.Data) {
                        pemesanPesananObatFragment = PemesanPesananObatFragment()
                        val bundle = Bundle()
                        bundle.putParcelable(
                            PemesanPesananObatFragment.DATA_PESANAN_OBAT,
                            position
                        )
                        pemesanPesananObatFragment.arguments = bundle
                        pemesanPesananObatFragment.setStyle(
                            DialogFragment.STYLE_NORMAL,
                            R.style.DialogFragmentTheme
                        )
                        pemesanPesananObatFragment.show(
                            supportFragmentManager.beginTransaction(),
                            "Pemesan Pesanan Obat"
                        )
                    }

                }

                pesananObatAdapter =
                    PesananObatAdapter(searchListPesanan, baseContext, clickActionPesanan)
                rv_pesanan_obat.adapter = pesananObatAdapter
                rv_pesanan_obat.layoutManager =
                    LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL, false)

            }

        }
    }

    private fun filterPesanan(search: String): ArrayList<PesananObatResponse.Data>? {
        val result: ArrayList<PesananObatResponse.Data> = ArrayList()
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

    override fun onPesananSuccess(it: ArrayList<PesananObatResponse.Data>) {
        et_search_pesananobat.setText("")
        this.listPesanan = it
        val clickActionPesanan = object : PesananObatAdapter.OnClickActionPesanan {
            override fun onClickDetailOrder(position: PesananObatResponse.Data) {
                val intent = Intent(this@PesananObatActivity, DetailPesananObatActivity::class.java)
                intent.putExtra("data", Gson().toJson(position))
                startActivity(intent)
            }

            override fun onClickChangeStatus(position: PesananObatResponse.Data) {
                updateStatusPesananObatFragment = UpdateStatusPesananObatFragment()
                val bundle = Bundle()
                bundle.putParcelable(
                    UpdateStatusPesananObatFragment.DATA_PESANAN_OBAT,
                    position
                )
                updateStatusPesananObatFragment.arguments = bundle
                updateStatusPesananObatFragment.setStyle(
                    DialogFragment.STYLE_NORMAL,
                    R.style.DialogFragmentTheme
                )
                updateStatusPesananObatFragment.show(
                    supportFragmentManager.beginTransaction(),
                    "Update Pesanan Obat"
                )
            }

            override fun onClickDetailPesanan(position: PesananObatResponse.Data) {
                detailPesananObatFragment = DetailPesananObatFragment()
                val bundle = Bundle()
                bundle.putString(DetailPesananObatFragment.ID_PESANAN_MOBILE, position.idPesananMobile)
                detailPesananObatFragment.arguments = bundle
                detailPesananObatFragment.setStyle(
                    DialogFragment.STYLE_NORMAL,
                    R.style.DialogFragmentTheme
                )
                detailPesananObatFragment.show(
                    supportFragmentManager.beginTransaction(),
                    "Detail Pesanan"
                )
            }

            override fun onClickDetailPemesan(position: PesananObatResponse.Data) {
                pemesanPesananObatFragment = PemesanPesananObatFragment()
                val bundle = Bundle()
                bundle.putParcelable(
                    PemesanPesananObatFragment.DATA_PESANAN_OBAT,
                    position
                )
                pemesanPesananObatFragment.arguments = bundle
                pemesanPesananObatFragment.setStyle(
                    DialogFragment.STYLE_NORMAL,
                    R.style.DialogFragmentTheme
                )
                pemesanPesananObatFragment.show(
                    supportFragmentManager.beginTransaction(),
                    "Pemesan Pesanan Obat"
                )
            }

        }
        pesananObatAdapter = PesananObatAdapter(it, this, clickActionPesanan)
        rv_pesanan_obat.adapter = pesananObatAdapter
        rv_pesanan_obat.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    override fun onProcess(boolean: Boolean) {
        if (boolean) {
            loading?.show()
        } else {
            loading?.dismiss()
        }
    }

    override fun onCloseFragmentUpdateStatusPesananObat(b: Boolean) {
        presenter.getPesanan(preferenceHelper?.getUser()!!.data.member[0].idMembers)
    }

}