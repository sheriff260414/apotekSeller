package id.owldevsoft.apotekseller.feature.masterdata.subgolonganobat.delete

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import id.owldevsoft.apotekseller.R
import id.owldevsoft.apotekseller.utils.DialogHelper
import kotlinx.android.synthetic.main.fragment_delete_sub_golongan_obat.*

/**
 * A simple [Fragment] subclass.
 * Use the [DeleteSubGolonganObatFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DeleteSubGolonganObatFragment : DialogFragment(), DeleteSubGolonganObatContract.View, View.OnClickListener {

    override lateinit var presenter: DeleteSubGolonganObatContract.Presenter
    private var mView: View? = null
    private var loading : AlertDialog? = null
    lateinit var mCallback: OnCloseFragmentDeleteSubGolonganObat

    var idSubGolonganObat: String? = null
    var namaSubGolonganObat: String? = null

    companion object {
        const val ID_SUB_GOLONGAN_OBAT: String = "IDSUBGOLONGANOBAT"
        const val NAMA_SUB_GOLONGAN_OBAT: String = "NAMASUBGOLONGANOBAT"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_delete_sub_golongan_obat, container, false)

        if (dialog != null && dialog!!.window != null) {
            dialog!!.window
                ?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog!!.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        }

        return mView

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DeleteSubGolonganObatPresenter(requireContext(), this)

        val bundle = arguments
        if (bundle != null) {
            idSubGolonganObat = bundle.getString(ID_SUB_GOLONGAN_OBAT)
            namaSubGolonganObat = bundle.getString(NAMA_SUB_GOLONGAN_OBAT)

            delete_nama_sub_golongan_obat.setText(namaSubGolonganObat)

        }

        loading = DialogHelper.showDialogLoading(requireContext())

        btnSaveDeleteSubGolonganObat.setOnClickListener(this)
        btnCancelDeleteSubGolonganObat.setOnClickListener(this)

    }

    override fun onDeleteError(code: Int, message: String) {
        DialogHelper.showWarnDialog(
            requireContext(),
            getString(R.string.err_mes, message),
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
        mCallback.onCloseFragmentDeleteSubGolonganObat(true)
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
            btnCancelDeleteSubGolonganObat.id -> this.dismiss()
            btnSaveDeleteSubGolonganObat.id -> presenter.deleteSubGolonganObat(idSubGolonganObat!!)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            mCallback = context as OnCloseFragmentDeleteSubGolonganObat
        } catch (e : ClassCastException){
            Log.d("Create Dialog", "Activity doesn't implement the OnCloseFragmentDeleteSubGolonganObat interface")
        }
    }

    interface OnCloseFragmentDeleteSubGolonganObat {
        fun onCloseFragmentDeleteSubGolonganObat(b: Boolean)
    }

}