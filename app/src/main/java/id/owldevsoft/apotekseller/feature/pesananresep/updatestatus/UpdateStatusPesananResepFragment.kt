package id.owldevsoft.apotekseller.feature.pesananresep.updatestatus

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import id.owldevsoft.apotekseller.R
import id.owldevsoft.apotekseller.model.PesananObatUpdateStatusBody
import id.owldevsoft.apotekseller.model.PesananResepResponse
import id.owldevsoft.apotekseller.model.PesananResepUpdateStatusBody
import id.owldevsoft.apotekseller.utils.DialogHelper
import kotlinx.android.synthetic.main.fragment_update_status_pesanan_obat.*
import kotlinx.android.synthetic.main.fragment_update_status_pesanan_resep.*
import kotlinx.android.synthetic.main.fragment_update_status_pesanan_resep.btnCancelUpdatestatus
import kotlinx.android.synthetic.main.fragment_update_status_pesanan_resep.btnSaveUpdatestatus
import kotlinx.android.synthetic.main.fragment_update_status_pesanan_resep.edit_biayatambahan
import kotlinx.android.synthetic.main.fragment_update_status_pesanan_resep.edit_biayatotal
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

class UpdateStatusPesananResepFragment : DialogFragment(), View.OnClickListener,
    UpdateStatusPesananResepContract.View {

    override lateinit var presenter: UpdateStatusPesananResepContract.Presenter
    private var mView: View? = null
    private var loading: AlertDialog? = null
    lateinit var mCallback: OnCloseFragmentUpdateStatusPesananResep
    private lateinit var pesananResepUpdateStatusBody: PesananResepUpdateStatusBody
    private var adapterStatus: ArrayAdapter<String>? = null

    lateinit var item: PesananResepResponse.Data
    private var namaStatus: String? = null
    private var idPesananMobile: Int? = null
    private var biayaTambahan: Int? = null
    private var biayaTotal: Int? = null
    private var posStatus: Int? = null

    companion object {
        const val DATA_PESANAN_RESEP: String = "DATAPESANANRESEP"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(R.layout.fragment_update_status_pesanan_resep, container, false)

        if (dialog != null && dialog!!.window != null) {
            dialog!!.window
                ?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog!!.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        }

        return mView

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        UpdateStatusPesananResepPresenter(requireContext(), this)
        loading = DialogHelper.showDialogLoading(requireContext())

        val bundle = arguments
        if (bundle != null) {
            item = bundle.getParcelable(DATA_PESANAN_RESEP)!!
            biayaTambahan = item.biayaPengiriman.toInt()
            biayaTotal = item.total.toInt()
            idPesananMobile = item.idResepMobile.toInt()

            edit_biayatambahan.setText(
                NumberFormat.getNumberInstance(Locale.US).format(biayaTambahan)
            )
            edit_biayatotal.setText(NumberFormat.getNumberInstance(Locale.US).format(biayaTotal))
            namaStatus = item.status
        }

        initSpStatus()
        edit_biayatambahan.addTextChangedListener(biayaTambahanChange())
        edit_biayatotal.addTextChangedListener(biayaTotalChange())

        btnSaveUpdatestatus.setOnClickListener(this)
        btnCancelUpdatestatus.setOnClickListener(this)

    }

    private fun biayaTambahanChange(): TextWatcher? {
        return object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun afterTextChanged(editable: Editable) {
                edit_biayatambahan.removeTextChangedListener(this)
                try {
                    var originalString = editable.toString()
                    val longval: Long
                    if (originalString.contains(",")) {
                        originalString = originalString.replace(",".toRegex(), "")
                    }
                    longval = originalString.toLong()
                    val formatter =
                        NumberFormat.getInstance(Locale.US) as DecimalFormat
                    formatter.applyPattern("#,###,###,###")
                    val formattedString = formatter.format(longval)

                    //setting text after format to EditText
                    edit_biayatambahan.setText(formattedString)
                    edit_biayatambahan.setSelection(edit_biayatambahan.text!!.length)
                } catch (nfe: NumberFormatException) {
                    nfe.printStackTrace()
                }
                edit_biayatambahan.addTextChangedListener(this)
            }
        }
    }

    private fun biayaTotalChange(): TextWatcher? {
        return object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun afterTextChanged(editable: Editable) {
                edit_biayatotal.removeTextChangedListener(this)
                try {
                    var originalString = editable.toString()
                    val longval: Long
                    if (originalString.contains(",")) {
                        originalString = originalString.replace(",".toRegex(), "")
                    }
                    longval = originalString.toLong()
                    val formatter =
                        NumberFormat.getInstance(Locale.US) as DecimalFormat
                    formatter.applyPattern("#,###,###,###")
                    val formattedString = formatter.format(longval)

                    //setting text after format to EditText
                    edit_biayatotal.setText(formattedString)
                    edit_biayatotal.setSelection(edit_biayatotal.text!!.length)
                } catch (nfe: NumberFormatException) {
                    nfe.printStackTrace()
                }
                edit_biayatotal.addTextChangedListener(this)
            }
        }
    }

    private fun initSpStatus() {
        adapterStatus?.clear()
        adapterStatus?.notifyDataSetChanged()
        adapterStatus = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            resources.getStringArray(R.array.status_pesanan)
        )
        sp_status_resep.adapter = adapterStatus
        posStatus = adapterStatus!!.getPosition(namaStatus)
        sp_status_resep.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                namaStatus = sp_status_resep.selectedItem.toString()
            }
        }
        sp_status_resep.setSelection(posStatus!!)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            btnSaveUpdatestatus.id -> {
                if (handleInput()) {
                    biayaTambahan = edit_biayatambahan.text.toString().trim { it <= ' ' }
                        .replace(",".toRegex(), "").toLong().toInt()
                    biayaTotal = edit_biayatotal.text.toString().trim { it <= ' ' }
                        .replace(",".toRegex(), "").toLong().toInt()

                    pesananResepUpdateStatusBody = PesananResepUpdateStatusBody(
                        biayaTambahan!!,
                        idPesananMobile!!,
                        namaStatus!!,
                        biayaTotal!!
                    )

                    presenter.updateStatusPesananResep(pesananResepUpdateStatusBody)

                }
            }
            btnCancelUpdatestatus.id -> {
                this.dismiss()
            }
        }
    }

    private fun handleInput(): Boolean {
        if (edit_biayatambahan.text.isNullOrEmpty()) {
            edit_biayatambahan.error = getString(R.string.fill_data)
            edit_biayatambahan.requestFocus()
            return false
        }
        if (edit_biayatotal.text.isNullOrEmpty()) {
            edit_biayatotal.error = getString(R.string.fill_data)
            edit_biayatotal.requestFocus()
            return false
        }
        return true
    }

    override fun onError(code: Int, message: String) {
        activity?.let {
            DialogHelper.showWarnDialog(
                it,
                message,
                getString(R.string.ans_mes_ok),
                false,
                object : DialogHelper.Positive {
                    override fun positiveButton(dialog: DialogInterface, id: Int) {
                        dialog.dismiss()
                    }
                })
        }
    }

    override fun onUpdateSuccess(message: String) {
        this.dismiss()
        mCallback.onCloseFragmentUpdateStatusPesananResep(true)
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
            mCallback = context as OnCloseFragmentUpdateStatusPesananResep
        } catch (e: ClassCastException) {
            Log.d(
                "Create Dialog",
                "Activity doesn't implement the OnCloseFragmentUpdateStatusPesananResep interface"
            )
        }
    }

    interface OnCloseFragmentUpdateStatusPesananResep {
        fun onCloseFragmentUpdateStatusPesananResep(b: Boolean)
    }

}