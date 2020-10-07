package id.owldevsoft.apotekseller.feature.masterdata.produsen.update

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
import id.owldevsoft.apotekseller.model.ProdusenUpdateBody
import id.owldevsoft.apotekseller.utils.DialogHelper
import kotlinx.android.synthetic.main.fragment_update_produsen.*

/**
 * A simple [Fragment] subclass.
 * Use the [UpdateProdusenFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UpdateProdusenFragment : DialogFragment(), View.OnClickListener, UpdateProdusenContract.View {

    private var mView: View? = null
    private var loading : AlertDialog? = null
    private lateinit var produsenUpdateBody: ProdusenUpdateBody
    lateinit var mCallback: OnCLoseFragmentUpdateProdusen

    override lateinit var presenter: UpdateProdusenContract.Presenter

    var idMerk: String? = null
    var namaMerk: String? = null

    companion object {
        const val ID_MERK: String = "IDMERK"
        const val NAMA_MERK: String = "NAMAMERK"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_update_produsen, container, false)

        if (dialog != null && dialog!!.window != null) {
            dialog!!.window
                ?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog!!.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        }

        return mView

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        UpdateProdusenPresenter(requireContext(), this)

        val bundle = arguments
        if (bundle != null){
            idMerk = bundle.getString(ID_MERK)
            namaMerk = bundle.getString(NAMA_MERK)

            edit_nama_produsen.setText(namaMerk)
        }

        loading = DialogHelper.showDialogLoading(requireContext())

        btnCancelUpdateProdusen.setOnClickListener(this)
        btnSaveUpdateProdusen.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            btnCancelUpdateProdusen.id -> this.dismiss()
            btnSaveUpdateProdusen.id -> {
                if (handleInput()) {
                    produsenUpdateBody = ProdusenUpdateBody(idMerk!!.toInt(), edit_nama_produsen.text.toString())
                    presenter.updateProdusen(produsenUpdateBody)
                }
            }

        }
    }

    private fun handleInput() : Boolean{
        if (edit_nama_produsen.text.isNullOrEmpty()){
            edit_nama_produsen.error = getString(R.string.fill_data)
            edit_nama_produsen.requestFocus()
            return false
        }
        return true
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            mCallback = context as OnCLoseFragmentUpdateProdusen
        } catch (e : ClassCastException){
            Log.d("Update Dialog", "Activity doesn't implement the OnCloseFragmentUpdateProdusen interface")
        }
    }

    interface OnCLoseFragmentUpdateProdusen {
        fun onCloseFragmentUpdateProdusen(b: Boolean)
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
        mCallback.onCloseFragmentUpdateProdusen(true)
    }

    override fun onProcess(boolean: Boolean) {
        if (boolean) {
            loading?.show()
        } else {
            loading?.dismiss()
        }
    }

}