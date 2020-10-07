package id.owldevsoft.apotekseller.feature.masterdata.jenisgolonganobat.delete

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
import kotlinx.android.synthetic.main.fragment_delete_jenis_golongan_obat.*

/**
 * A simple [Fragment] subclass.
 * Use the [DeleteJenisGolonganObatFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DeleteJenisGolonganObatFragment : DialogFragment(), DeleteJenisGolonganObatContract.View,
    View.OnClickListener {

    override lateinit var presenter: DeleteJenisGolonganObatContract.Presenter
    private var mView: View? = null
    private var loading: AlertDialog? = null
    lateinit var mCallback: OnCloseFragmentDeleteJenisGolonganObat

    var idJenisGolonganObat: String? = null
    var namaJenisGolonganObat: String? = null

    companion object {
        const val ID_JENIS_GOLONGAN_OBAT: String = "IDJENISGOLONGANOBAT"
        const val NAMA_JENIS_GOLONGAN_OBAT: String = "NAMAJENISGOLONGANOBAT"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_delete_jenis_golongan_obat, container, false)

        if (dialog != null && dialog!!.window != null) {
            dialog!!.window
                ?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog!!.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        }

        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DeleteJenisGolonganObatPresenter(requireContext(), this)

        val bundle = arguments
        if (bundle != null) {
            idJenisGolonganObat = bundle.getString(ID_JENIS_GOLONGAN_OBAT)
            namaJenisGolonganObat = bundle.getString(NAMA_JENIS_GOLONGAN_OBAT)

            delete_nama_jenis_golongan_obat.setText(namaJenisGolonganObat)

        }

        loading = DialogHelper.showDialogLoading(requireContext())

        btnSaveDeleteJenisGolonganObat.setOnClickListener(this)
        btnCancelDeleteJenisGolonganObat.setOnClickListener(this)

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
        mCallback.onCloseFragmentDeleteJenisGolonganObat(true)
    }

    override fun onProcess(boolean: Boolean) {
        if (boolean) {
            loading?.show()
        } else {
            loading?.dismiss()
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            btnCancelDeleteJenisGolonganObat.id -> this.dismiss()
            btnSaveDeleteJenisGolonganObat.id -> presenter.deleteJenisGolonganObat(
                idJenisGolonganObat!!
            )
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            mCallback = context as OnCloseFragmentDeleteJenisGolonganObat
        } catch (e: ClassCastException) {
            Log.d(
                "Create Dialog",
                "Activity doesn't implement the OnCloseFragmentDeleteProdusen interface"
            )
        }
    }

    interface OnCloseFragmentDeleteJenisGolonganObat {
        fun onCloseFragmentDeleteJenisGolonganObat(b: Boolean)
    }

}