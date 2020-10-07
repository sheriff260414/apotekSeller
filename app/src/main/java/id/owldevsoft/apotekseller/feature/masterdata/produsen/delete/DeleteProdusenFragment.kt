package id.owldevsoft.apotekseller.feature.masterdata.produsen.delete

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
import kotlinx.android.synthetic.main.fragment_delete_produsen.*

/**
 * A simple [Fragment] subclass.
 * Use the [DeleteProdusenFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DeleteProdusenFragment : DialogFragment(), DeleteProdusenContract.View, View.OnClickListener {

    override lateinit var presenter: DeleteProdusenContract.Presenter
    private var mView: View? = null
    private var loading : AlertDialog? = null
    lateinit var mCallback: OnCloseFragmentDeleteProdusen

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
        mView = inflater.inflate(R.layout.fragment_delete_produsen, container, false)

        if (dialog != null && dialog!!.window != null) {
            dialog!!.window
                ?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog!!.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        }

        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DeleteProdusenPresenter(requireContext(), this)

        val bundle = arguments
        if (bundle != null){
            idMerk = bundle.getString(ID_MERK)
            namaMerk = bundle.getString(NAMA_MERK)

            delete_nama_produsen.setText(namaMerk)

        }

        loading = DialogHelper.showDialogLoading(requireContext())

        btnSaveDeleteProdusen.setOnClickListener(this)
        btnCancelDeleteProdusen.setOnClickListener(this)

    }

    override fun onDeleteError(code: Int, message: String) {
        DialogHelper.showWarnDialog(requireContext(), getString(R.string.err_mes, message), getString(R.string.ans_mes_ok), false, object : DialogHelper.Positive {
            override fun positiveButton(dialog: DialogInterface, id: Int)
            {
                dialog.dismiss()
            }
        })
    }

    override fun onDeleteSuccess(message: String) {
        this.dismiss()
        mCallback.onCloseFragmentDeleteProdusen(true)
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
            mCallback = context as OnCloseFragmentDeleteProdusen
        } catch (e : ClassCastException){
            Log.d("Create Dialog", "Activity doesn't implement the OnCloseFragmentDeleteProdusen interface")
        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            btnCancelDeleteProdusen.id -> this.dismiss()
            btnSaveDeleteProdusen.id -> presenter.deleteProdusen(idMerk!!)
        }
    }

    interface OnCloseFragmentDeleteProdusen{
        fun onCloseFragmentDeleteProdusen(b: Boolean)
    }

}