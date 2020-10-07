package id.owldevsoft.apotekseller.feature.pesananobat.detailpesanan

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import id.owldevsoft.apotekseller.R
import id.owldevsoft.apotekseller.adapter.PesananObatDetailAdapter
import id.owldevsoft.apotekseller.model.PesananObatDetailResponse
import id.owldevsoft.apotekseller.model.PesananObatResponse
import id.owldevsoft.apotekseller.utils.DialogHelper
import kotlinx.android.synthetic.main.fragment_detail_pesanan_obat.*

class DetailPesananObatFragment: DialogFragment(), DetailPesananObatContract.View {

    override lateinit var presenter: DetailPesananObatContract.Presenter
    private var mView: View? = null
    private var loading: AlertDialog? = null
    private lateinit var pesananObatDetailAdapter: PesananObatDetailAdapter

    lateinit var item: PesananObatResponse.Data

    var idPesananMobile: String? = null

    companion object {
        const val ID_PESANAN_MOBILE: String = "IDPESANANMOBILE"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(R.layout.fragment_detail_pesanan_obat, container, false)

        if (dialog != null && dialog!!.window != null) {
            dialog!!.window
                ?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog!!.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        }

        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DetailPesananObatPresenter(requireContext(), this)
        loading = DialogHelper.showDialogLoading(requireContext())

        val bundle = arguments
        if (bundle != null){
            idPesananMobile = bundle.getString(ID_PESANAN_MOBILE)
            presenter.getDetailPesanan(idPesananMobile!!)
        }

        close.setOnClickListener {
            this.dismiss()
        }

    }

    override fun onError(code: Int, message: String) {
        activity?.let {
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

    override fun onDetailSuccess(it: ArrayList<PesananObatDetailResponse.Data>) {
        pesananObatDetailAdapter = PesananObatDetailAdapter(it, requireContext())
        rv_detal_pesanan_obat.adapter = pesananObatDetailAdapter
        rv_detal_pesanan_obat.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    override fun onProcess(boolean: Boolean) {
        if (boolean) {
            loading?.show()
        } else {
            loading?.dismiss()
        }
    }

}