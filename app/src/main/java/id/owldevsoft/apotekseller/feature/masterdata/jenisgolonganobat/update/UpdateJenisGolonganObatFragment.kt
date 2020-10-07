package id.owldevsoft.apotekseller.feature.masterdata.jenisgolonganobat.update

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
import id.owldevsoft.apotekseller.model.JenisObatUpdateBody
import id.owldevsoft.apotekseller.utils.DialogHelper
import kotlinx.android.synthetic.main.fragment_update_jenis_golongan_obat.*

/**
 * A simple [Fragment] subclass.
 * Use the [UpdateJenisGolonganObatFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UpdateJenisGolonganObatFragment : DialogFragment(), View.OnClickListener,
    UpdateJenisGolonganObatContract.View {

    private var mView: View? = null
    private var loading: AlertDialog? = null
    private lateinit var jenisObatUpdateBody: JenisObatUpdateBody
    lateinit var mCallback: OnCLoseFragmentUpdateJenisGolonganObat

    override lateinit var presenter: UpdateJenisGolonganObatContract.Presenter

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
        mView = inflater.inflate(R.layout.fragment_update_jenis_golongan_obat, container, false)

        if (dialog != null && dialog!!.window != null) {
            dialog!!.window
                ?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog!!.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        }

        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        UpdateJenisGolonganObatPresenter(requireContext(), this)

        val bundle = arguments
        if (bundle != null) {
            idJenisGolonganObat = bundle.getString(ID_JENIS_GOLONGAN_OBAT)
            namaJenisGolonganObat = bundle.getString(NAMA_JENIS_GOLONGAN_OBAT)

            edit_nama_jenis_golongan_obat.setText(namaJenisGolonganObat)
        }

        loading = DialogHelper.showDialogLoading(requireContext())

        btnCancelUpdateJenisGolonganObat.setOnClickListener(this)
        btnSaveUpdateJenisGolonganObat.setOnClickListener(this)

    }

    override fun onUpdateError(code: Int, message: String) {
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

    override fun onUpdateSuccess(message: String) {
        this.dismiss()
        mCallback.onCloseFragmentUpdateJenisGolonganObat(true)
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
            btnCancelUpdateJenisGolonganObat.id -> this.dismiss()
            btnSaveUpdateJenisGolonganObat.id -> {
                if (handleInput()) {
                    jenisObatUpdateBody = JenisObatUpdateBody(
                        idJenisGolonganObat!!.toInt(),
                        edit_nama_jenis_golongan_obat.text.toString()
                    )
                    presenter.updateProdusen(jenisObatUpdateBody)
                }
            }

        }
    }

    private fun handleInput(): Boolean {
        if (edit_nama_jenis_golongan_obat.text.isNullOrEmpty()) {
            edit_nama_jenis_golongan_obat.error = getString(R.string.fill_data)
            edit_nama_jenis_golongan_obat.requestFocus()
            return false
        }
        return true
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            mCallback = context as OnCLoseFragmentUpdateJenisGolonganObat
        } catch (e: ClassCastException) {
            Log.d(
                "Update Dialog",
                "Activity doesn't implement the OnCloseFragmentUpdateJenisGolonganObat interface"
            )
        }
    }

    interface OnCLoseFragmentUpdateJenisGolonganObat {
        fun onCloseFragmentUpdateJenisGolonganObat(b: Boolean)
    }

}