package id.owldevsoft.apotekseller.feature.penjualan.read

import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import id.owldevsoft.apotekseller.R
import id.owldevsoft.apotekseller.adapter.PenjualanAdapter
import id.owldevsoft.apotekseller.feature.masterdata.subgolonganobat.delete.DeleteSubGolonganObatFragment
import id.owldevsoft.apotekseller.feature.masterdata.subgolonganobat.update.UpdateSubGolonganObatFragment
import id.owldevsoft.apotekseller.feature.penjualan.delete.DeletePenjualanFragment
import id.owldevsoft.apotekseller.feature.penjualan.update.UpdatePenjualanFragment
import id.owldevsoft.apotekseller.model.PenjualanResponse
import id.owldevsoft.apotekseller.utils.DialogHelper
import id.owldevsoft.apotekseller.utils.PreferenceHelper
import kotlinx.android.synthetic.main.action_bar.*
import kotlinx.android.synthetic.main.activity_penjualan.*

class PenjualanActivity : AppCompatActivity(),
    PenjualanContract.View,
    UpdatePenjualanFragment.OnCLoseFragmentUpdatePenjualan,
    DeletePenjualanFragment.OnCloseFragmentDeletePenjualan {

    override lateinit var presenter: PenjualanContract.Presenter
    private var preferenceHelper: PreferenceHelper? = null

    private var loading: AlertDialog? = null
    private var penjualanAdapter: PenjualanAdapter? = null

    private lateinit var updatePenjualFragment: UpdatePenjualanFragment
    private lateinit var deletePenjualanFragment: DeletePenjualanFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_penjualan)

        PenjualanPresenter(
            this,
            this
        )
        preferenceHelper = PreferenceHelper(this)

        titlebar.text = "Data Penjualan"
        loading = DialogHelper.showDialogLoading(this)
        presenter.getPenjualan(preferenceHelper?.getUser()!!.data.member[0].idMembers)

        back.setOnClickListener {
            finish()
        }

    }

    override fun onPenjualanError(code: Int, message: String) {
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

    override fun onPenjualanSuccess(it: ArrayList<PenjualanResponse.Data>) {

        val clickAction = object : PenjualanAdapter.OnClickActionPenjualan {
            override fun onCLickUpdate(position: PenjualanResponse.Data?) {
                updatePenjualFragment = UpdatePenjualanFragment()
                val bundle = Bundle()
                bundle.putParcelable(
                    UpdatePenjualanFragment.DATA_PENJUALAN,
                    position
                )
                updatePenjualFragment.arguments = bundle
                updatePenjualFragment.setStyle(
                    DialogFragment.STYLE_NORMAL,
                    R.style.DialogFragmentTheme
                )
                updatePenjualFragment.show(
                    supportFragmentManager.beginTransaction(),
                    "Update Penjualan"
                )
            }

            override fun onClickDelete(position: PenjualanResponse.Data?) {
                deletePenjualanFragment = DeletePenjualanFragment()
                val bundle = Bundle()
                bundle.putString(
                    DeletePenjualanFragment.ID_OBAT_MOBILE,
                    position?.idObatMobile
                )
                bundle.putString(
                    DeletePenjualanFragment.NAMA_PENJUALAN,
                    position?.namaObat
                )
                deletePenjualanFragment.arguments = bundle
                deletePenjualanFragment.setStyle(
                    DialogFragment.STYLE_NORMAL,
                    R.style.DialogFragmentTheme
                )
                deletePenjualanFragment.show(
                    supportFragmentManager.beginTransaction(),
                    "Delete Penjualan"
                )
            }

        }

        penjualanAdapter = PenjualanAdapter(it, this, clickAction)
        rv_penjualan.adapter = penjualanAdapter
        rv_penjualan.layoutManager =
            LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL, false)
    }

    override fun onProcess(boolean: Boolean) {
        if (boolean) {
            loading?.show()
        } else {
            loading?.dismiss()
        }
    }

    override fun onCloseFragmentUpdatePenjualan(b: Boolean) {
        presenter.getPenjualan(preferenceHelper?.getUser()!!.data.member[0].idMembers)
    }

    override fun onCloseFragmentDeletePenjualan(b: Boolean) {
        presenter.getPenjualan(preferenceHelper?.getUser()!!.data.member[0].idMembers)
    }

}