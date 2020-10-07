package id.owldevsoft.apotekseller.feature.masterdata.satuan.delete

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
import kotlinx.android.synthetic.main.fragment_delete_satuan.*

/**
 * A simple [Fragment] subclass.
 * Use the [DeleteSatuanFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DeleteSatuanFragment : DialogFragment(), DeleteSatuanContract.View, View.OnClickListener {

    override lateinit var presenter: DeleteSatuanContract.Presenter
    private var mView: View? = null
    private var loading : AlertDialog? = null
    lateinit var mCallback: OnCloseFragmentDeleteSatuan

    var idSatuan: String? = null
    var namaSatuan: String? = null

    companion object {
        const val ID_SATUAN: String = "IDSATUAN"
        const val NAMA_SATUAN: String = "NAMASATUAN"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_delete_satuan, container, false)

        if (dialog != null && dialog!!.window != null) {
            dialog!!.window
                ?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog!!.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        }

        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DeleteSatuanPresenter(requireContext(), this)

        val bundle = arguments
        if (bundle != null){
            idSatuan = bundle.getString(ID_SATUAN)
            namaSatuan = bundle.getString(NAMA_SATUAN)

            delete_nama_satuan.setText(namaSatuan)

        }

        loading = DialogHelper.showDialogLoading(requireContext())

        btnSaveDeleteSatuan.setOnClickListener(this)
        btnCancelDeleteSatuan.setOnClickListener(this)

    }

    override fun onDeleteError(code: Int, message: String) {
        DialogHelper.showWarnDialog(requireContext(), message, getString(R.string.ans_mes_ok), false, object : DialogHelper.Positive {
            override fun positiveButton(dialog: DialogInterface, id: Int)
            {
                dialog.dismiss()
            }
        })
    }

    override fun onDeleteSuccess(message: String) {
        this.dismiss()
        mCallback.onCloseFragmentDeleteSatuan(true)
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
            mCallback = context as OnCloseFragmentDeleteSatuan
        } catch (e : ClassCastException){
            Log.d("Create Dialog", "Activity doesn't implement the OnCloseFragmentDeleteSatuan interface")
        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            btnCancelDeleteSatuan.id -> this.dismiss()
            btnSaveDeleteSatuan.id -> presenter.deleteSatuan(idSatuan!!)
        }
    }

    interface OnCloseFragmentDeleteSatuan{
        fun onCloseFragmentDeleteSatuan(b: Boolean)
    }

}