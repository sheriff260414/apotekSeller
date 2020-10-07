package id.owldevsoft.apotekseller.feature.masterproduk.delete

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
import id.owldevsoft.apotekseller.model.ObatResponse
import id.owldevsoft.apotekseller.utils.DialogHelper
import kotlinx.android.synthetic.main.fragment_delete_data_obat.*

/**
 * A simple [Fragment] subclass.
 * Use the [DeleteDataObatFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DeleteDataObatFragment : DialogFragment(), DeleteDataObatContract.View, View.OnClickListener {

    override lateinit var presenter: DeleteDataObatContract.Presenter
    private var mView: View? = null
    private var loading: AlertDialog? = null
    lateinit var mCallback: OnCloseFragmentDeleteObat

    lateinit var item: ObatResponse.Data

    companion object {
        const val DATA_OBAT: String = "DATAOBAT"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_delete_data_obat, container, false)

        if (dialog != null && dialog!!.window != null) {
            dialog!!.window
                ?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog!!.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        }

        return mView

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DeleteDataObatPresenter(requireContext(), this)

        val bundle = arguments
        if (bundle != null) {
            item = bundle.getParcelable(DATA_OBAT)!!
            delete_data_obat.setText(item.namaObat)
        }

        loading = DialogHelper.showDialogLoading(requireContext())
        btnSaveDeleteDataObat.setOnClickListener(this)
        btnCancelDeleteDataObat.setOnClickListener(this)

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
        mCallback.onCloseFragmentDeleteDataObat(true)
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
            btnSaveDeleteDataObat.id -> {
                presenter.deleteDataObat(item.idObat)
            }
            btnCancelDeleteDataObat.id -> {
                this.dismiss()
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            mCallback = context as OnCloseFragmentDeleteObat
        } catch (e: ClassCastException) {
            Log.d(
                "Create Dialog",
                "Activity doesn't implement the OnCloseFragmentDeleteObat interface"
            )
        }
    }

    interface OnCloseFragmentDeleteObat {
        fun onCloseFragmentDeleteDataObat(b: Boolean)
    }

}