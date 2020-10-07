package id.owldevsoft.apotekseller.feature.masterdata.subgolonganobat.update

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
import id.owldevsoft.apotekseller.model.SubGolonganObatUpdateBody
import id.owldevsoft.apotekseller.utils.DialogHelper
import kotlinx.android.synthetic.main.fragment_update_sub_golongan_obat.*

/**
 * A simple [Fragment] subclass.
 * Use the [UpdateSubGolonganObatFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UpdateSubGolonganObatFragment : DialogFragment(), View.OnClickListener,
    UpdateSubGolonganObatContract.View {

    private var mView: View? = null
    private var loading: AlertDialog? = null
    private lateinit var subGolonganObatUpdateBody: SubGolonganObatUpdateBody
    lateinit var mCallback: OnCLoseFragmentUpdateSubGolonganObat

    var idSubGolonganObat: String? = null
    var namaSubGolonganObat: String? = null

    companion object {
        const val ID_SUB_GOLONGAN_OBAT: String = "IDSUBGOLONGANOBAT"
        const val NAMA_SUB_GOLONGAN_OBAT: String = "NAMASUBGOLONGANOBAT"
    }

    override lateinit var presenter: UpdateSubGolonganObatContract.Presenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_update_sub_golongan_obat, container, false)

        if (dialog != null && dialog!!.window != null) {
            dialog!!.window
                ?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog!!.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        }

        return mView

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        UpdateSubGolonganObatPresenter(requireContext(), this)

        val bundle = arguments
        if (bundle != null) {
            idSubGolonganObat = bundle.getString(ID_SUB_GOLONGAN_OBAT)
            namaSubGolonganObat = bundle.getString(NAMA_SUB_GOLONGAN_OBAT)

            edit_nama_sub_golongan_obat.setText(namaSubGolonganObat)

        }

        loading = DialogHelper.showDialogLoading(requireContext())

        btnCancelUpdateSubGolonganObat.setOnClickListener(this)
        btnSaveUpdateSubGolonganObat.setOnClickListener(this)

    }

    private fun handleInput(): Boolean {
        if (edit_nama_sub_golongan_obat.text.isNullOrEmpty()) {
            edit_nama_sub_golongan_obat.error = getString(R.string.fill_data)
            edit_nama_sub_golongan_obat.requestFocus()
            return false
        }
        return true
    }

    override fun onUpdateError(code: Int, message: String) {
        DialogHelper.showWarnDialog(requireContext(), getString(R.string.err_mes, message), getString(R.string.ans_mes_ok), false, object : DialogHelper.Positive {
            override fun positiveButton(dialog: DialogInterface, id: Int)
            {
                dialog.dismiss()
            }
        })
    }

    override fun onUpdateSuccess(message: String) {
        this.dismiss()
        mCallback.onCloseFragmentUpdateSubGolonganObat(true)
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
            btnCancelUpdateSubGolonganObat.id -> this.dismiss()
            btnSaveUpdateSubGolonganObat.id -> {
                if (handleInput()) {
                    subGolonganObatUpdateBody = SubGolonganObatUpdateBody(
                        idSubGolonganObat!!.toInt(),
                        edit_nama_sub_golongan_obat.text.toString()
                    )
                    presenter.updateSatuan(subGolonganObatUpdateBody)
                }
            }

        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            mCallback = context as OnCLoseFragmentUpdateSubGolonganObat
        } catch (e : ClassCastException){
            Log.d("Update Dialog", "Activity doesn't implement the OnCloseFragmentUpdateSatuan interface")
        }
    }

    interface OnCLoseFragmentUpdateSubGolonganObat {
        fun onCloseFragmentUpdateSubGolonganObat(b: Boolean)
    }

}