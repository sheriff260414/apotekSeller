package id.owldevsoft.apotekseller.feature.masterdata.subgolonganobat.create

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
import id.owldevsoft.apotekseller.model.SubGolonganObatCreateBody
import id.owldevsoft.apotekseller.utils.DialogHelper
import id.owldevsoft.apotekseller.utils.PreferenceHelper
import kotlinx.android.synthetic.main.fragment_create_sub_golongan_obat.*

/**
 * A simple [Fragment] subclass.
 * Use the [CreateSubGolonganObatFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CreateSubGolonganObatFragment : DialogFragment(), CreateSubGolonganObatContract.View,
    View.OnClickListener {

    override lateinit var presenter: CreateSubGolonganObatContract.Presenter
    private var preferenceHelper: PreferenceHelper? = null

    private var mView: View? = null
    private var loading: AlertDialog? = null
    private lateinit var subGolonganObatCreateBody: SubGolonganObatCreateBody
    lateinit var mCallback: OnCloseFragmentCreateSubGolonganObat

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_create_sub_golongan_obat, container, false)

        if (dialog != null && dialog!!.window != null) {
            dialog!!.window
                ?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog!!.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        }

        return mView

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        CreateSubGolonganObatPresenter(requireContext(), this)
        preferenceHelper = PreferenceHelper(requireContext())

        loading = DialogHelper.showDialogLoading(requireContext())

        btnSaveCreateSubGolongan.setOnClickListener(this)
        btnCancelCreateSubGolongan.setOnClickListener(this)
    }

    override fun onCreateError(code: Int, message: String) {
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

    override fun onCreateSuccess(message: String) {
        this.dismiss()
        mCallback.onCloseFragmentCreateSubGolonganObat(true)
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
            btnCancelCreateSubGolongan.id -> this.dismiss()
            btnSaveCreateSubGolongan.id -> {
                if (handleInput()) {
                    subGolonganObatCreateBody = SubGolonganObatCreateBody(
                        preferenceHelper?.getUser()!!.data.member[0].idMembers.toInt(),
                        create_nama_sub_golongan.text.toString()
                    )
                    presenter.createSubGolonganObat(subGolonganObatCreateBody)
                }
            }
        }
    }

    private fun handleInput(): Boolean {
        if (create_nama_sub_golongan.text.isNullOrEmpty()) {
            create_nama_sub_golongan.error = getString(R.string.fill_data)
            create_nama_sub_golongan.requestFocus()
            return false
        }
        return true
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            mCallback = context as OnCloseFragmentCreateSubGolonganObat
        } catch (e: ClassCastException) {
            Log.d(
                "Create Dialog",
                "Activity doesn't implement the OnCloseFragmentCreateSubGolonganObat interface"
            )
        }
    }

    interface OnCloseFragmentCreateSubGolonganObat {
        fun onCloseFragmentCreateSubGolonganObat(b: Boolean)
    }

}