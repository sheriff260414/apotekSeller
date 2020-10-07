package id.owldevsoft.apotekseller.feature.masterdata.produsen.create

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
import id.owldevsoft.apotekseller.model.ProdusenCreateBody
import id.owldevsoft.apotekseller.utils.DialogHelper
import id.owldevsoft.apotekseller.utils.PreferenceHelper
import kotlinx.android.synthetic.main.fragment_create_produsen.*

/**
 * A simple [Fragment] subclass.
 * Use the [CreateProdusenFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CreateProdusenFragment : DialogFragment(), CreateProdusenContract.View, View.OnClickListener {

    override lateinit var presenter: CreateProdusenContract.Presenter
    private var preferenceHelper: PreferenceHelper? = null

    private var mView: View? = null
    private var loading : AlertDialog? = null
    private lateinit var produsenCreateBody: ProdusenCreateBody
    lateinit var mCallback: OnCloseFragmentCreateProdusen

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_create_produsen, container, false)

        if (dialog != null && dialog!!.window != null) {
            dialog!!.window
                ?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog!!.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        }

        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CreateProdusenPresenter(requireContext(), this)
        preferenceHelper = PreferenceHelper(requireContext())

        loading = DialogHelper.showDialogLoading(requireContext())

        btnSaveCreateProdusen.setOnClickListener(this)
        btnCancelCreateProdusen.setOnClickListener(this)

    }

    override fun onCreateError(code: Int, message: String) {
        DialogHelper.showWarnDialog(requireContext(), getString(R.string.err_mes, message), getString(R.string.ans_mes_ok), false, object : DialogHelper.Positive {
            override fun positiveButton(dialog: DialogInterface, id: Int)
            {
                dialog.dismiss()
            }
        })
    }

    override fun onCreateSuccess(message: String) {
        this.dismiss()
        mCallback.onCloseFragmentCreateProdusen(true)
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
            mCallback = context as OnCloseFragmentCreateProdusen
        } catch (e : ClassCastException){
            Log.d("Create Dialog", "Activity doesn't implement the OnCloseFragmentCreateProdusen interface")
        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            btnCancelCreateProdusen.id -> this.dismiss()
            btnSaveCreateProdusen.id -> {
                if (handleInput()){
                    produsenCreateBody = ProdusenCreateBody(preferenceHelper?.getUser()!!.data.member[0].idMembers.toInt(), create_nama_produsen.text.toString())
                    presenter.createProdusen(produsenCreateBody)
                }
            }
        }
    }

    private fun handleInput() : Boolean{
        if (create_nama_produsen.text.isNullOrEmpty()){
            create_nama_produsen.error = getString(R.string.fill_data)
            create_nama_produsen.requestFocus()
            return false
        }
        return true
    }

    interface OnCloseFragmentCreateProdusen{
        fun onCloseFragmentCreateProdusen(b: Boolean)
    }

}