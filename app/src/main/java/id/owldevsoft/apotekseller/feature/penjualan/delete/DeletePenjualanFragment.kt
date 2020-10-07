package id.owldevsoft.apotekseller.feature.penjualan.delete

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import id.owldevsoft.apotekseller.R
import id.owldevsoft.apotekseller.feature.masterdata.subgolonganobat.delete.DeleteSubGolonganObatContract
import id.owldevsoft.apotekseller.feature.masterdata.subgolonganobat.delete.DeleteSubGolonganObatFragment
import id.owldevsoft.apotekseller.utils.DialogHelper
import kotlinx.android.synthetic.main.fragment_delete_penjualan.*
import kotlinx.android.synthetic.main.fragment_delete_sub_golongan_obat.*

/**
 * A simple [Fragment] subclass.
 * Use the [DeletePenjualanFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DeletePenjualanFragment : DialogFragment(), DeletePenjualanContract.View,
    View.OnClickListener {

    override lateinit var presenter: DeletePenjualanContract.Presenter
    private var mView: View? = null
    private var loading: AlertDialog? = null
    lateinit var mCallback: OnCloseFragmentDeletePenjualan

    var idObatMobile: String? = null
    var namaPenjualan: String? = null

    companion object {
        const val ID_OBAT_MOBILE: String = "IDOBATMOBILE"
        const val NAMA_PENJUALAN: String = "NAMAPENJUALAN"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_delete_penjualan, container, false)

        if (dialog != null && dialog!!.window != null) {
            dialog!!.window
                ?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog!!.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        }

        return mView

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DeletePenjualanPresenter(requireContext(), this)

        val bundle = arguments
        if (bundle != null) {
            idObatMobile = bundle.getString(ID_OBAT_MOBILE)
            namaPenjualan = bundle.getString(NAMA_PENJUALAN)

            delete_nama_penjualan.setText(namaPenjualan)

        }

        loading = DialogHelper.showDialogLoading(requireContext())

        btnSaveDeletePenjualan.setOnClickListener(this)
        btnCancelDeletePenjualan.setOnClickListener(this)

    }

    override fun onDeleteError(code: Int, message: String) {
        DialogHelper.showWarnDialog(
            requireContext(),
            message,
            getString(R.string.ans_mes_ok),
            false,
            object : DialogHelper.Positive {
                override fun positiveButton(dialog: DialogInterface, id: Int) {
                    dialog.dismiss()
                }
            })
    }

    override fun onDeleteSuccess(message: String) {
        this.dismiss()
        mCallback.onCloseFragmentDeletePenjualan(true)
    }

    override fun onProcess(boolean: Boolean) {
        if (boolean) {
            loading?.show()
        } else {
            loading?.dismiss()
        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            btnCancelDeletePenjualan.id -> this.dismiss()
            btnSaveDeletePenjualan.id -> presenter.deletePenjualan(idObatMobile!!)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            mCallback = context as OnCloseFragmentDeletePenjualan
        } catch (e : ClassCastException){
            Log.d("Create Dialog", "Activity doesn't implement the OnCloseFragmentDeletePenjualan interface")
        }
    }

    interface OnCloseFragmentDeletePenjualan {
        fun onCloseFragmentDeletePenjualan(b: Boolean)
    }

}