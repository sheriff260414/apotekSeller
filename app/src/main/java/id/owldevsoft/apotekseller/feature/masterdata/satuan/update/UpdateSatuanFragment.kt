package id.owldevsoft.apotekseller.feature.masterdata.satuan.update

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
import id.owldevsoft.apotekseller.model.SatuanUpdateBody
import id.owldevsoft.apotekseller.utils.DialogHelper
import kotlinx.android.synthetic.main.fragment_update_satuan.*

/**
 * A simple [Fragment] subclass.
 * Use the [UpdateSatuanFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UpdateSatuanFragment : DialogFragment(), View.OnClickListener, UpdateSatuanContract.View {

    private var mView: View? = null
    private var loading : AlertDialog? = null
    private lateinit var satuanUpdateBody: SatuanUpdateBody
    lateinit var mCallback: OnCLoseFragmentUpdateSatuan

    var idSatuan: String? = null
    var namaSatuan: String? = null

    companion object {
        const val ID_SATUAN: String = "IDSATUAN"
        const val NAMA_SATUAN: String = "NAMASATUAN"
    }

    override lateinit var presenter: UpdateSatuanContract.Presenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_update_satuan, container, false)

        if (dialog != null && dialog!!.window != null) {
            dialog!!.window
                ?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog!!.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        }

//        dialog!!.window!!.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.CENTER)
//        val p = dialog!!.window!!.attributes
//        p.width = ViewGroup.LayoutParams.MATCH_PARENT
//        p.height = ViewGroup.LayoutParams.WRAP_CONTENT
//
//        dialog!!.window!!.attributes = p

        return mView

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        UpdateSatuanPresenter(requireContext(), this)

        val bundle = arguments
        if (bundle != null){
            idSatuan = bundle.getString(ID_SATUAN)
            namaSatuan = bundle.getString(NAMA_SATUAN)

            edit_nama_satuan.setText(namaSatuan)
            
        }

        loading = DialogHelper.showDialogLoading(requireContext())

        btnCancelUpdateSatuan.setOnClickListener(this)
        btnSaveUpdateSatuan.setOnClickListener(this)

    }

    private fun handleInput() : Boolean{
        if (edit_nama_satuan.text.isNullOrEmpty()){
            edit_nama_satuan.error = getString(R.string.fill_data)
            edit_nama_satuan.requestFocus()
            return false
        }
        return true
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            btnCancelUpdateSatuan.id -> this.dismiss()
            btnSaveUpdateSatuan.id -> {
                if (handleInput()) {
                    satuanUpdateBody = SatuanUpdateBody(idSatuan!!.toInt(), edit_nama_satuan.text.toString())
                    presenter.updateSatuan(satuanUpdateBody)
                }
            }

        }
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
        mCallback.onCloseFragmentUpdateSatuan(true)
    }

    override fun onProcess(boolean: Boolean) {
        if (boolean) {
            loading?.show()
        } else {
            loading?.dismiss()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            mCallback = context as OnCLoseFragmentUpdateSatuan
        } catch (e : ClassCastException){
            Log.d("Update Dialog", "Activity doesn't implement the OnCloseFragmentUpdateSatuan interface")
        }
    }

    interface OnCLoseFragmentUpdateSatuan {
        fun onCloseFragmentUpdateSatuan(b: Boolean)
    }

}