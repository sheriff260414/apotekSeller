package id.owldevsoft.apotekseller.feature.penjualan.update

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
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import id.owldevsoft.apotekseller.R
import id.owldevsoft.apotekseller.feature.masterdata.jenisgolonganobat.update.UpdateJenisGolonganObatFragment
import id.owldevsoft.apotekseller.model.AddPenjualanBody
import id.owldevsoft.apotekseller.model.PenjualanResponse
import id.owldevsoft.apotekseller.utils.DialogHelper
import kotlinx.android.synthetic.main.fragment_update_penjualan.*
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [UpdatePenjualanFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UpdatePenjualanFragment : DialogFragment(), UpdatePenjualanContract.View,
    View.OnClickListener {

    override lateinit var presenter: UpdatePenjualanContract.Presenter
    private var mView: View? = null
    private var loading: AlertDialog? = null
    private lateinit var updatePenjualanBody: AddPenjualanBody.updatePenjualanBody
    lateinit var mCallback: OnCLoseFragmentUpdatePenjualan

    lateinit var item: PenjualanResponse.Data

    var idObat: Int? = null
    var idSatuan: Int? = null
    var idObatMobile: Int? = null
    var harga: Int? = null
    var kategori: String? = null

    companion object {
        const val DATA_PENJUALAN: String = "DATAPENJUALAN"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_update_penjualan, container, false)

        if (dialog != null && dialog!!.window != null) {
            dialog!!.window
                ?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog!!.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        }

        return mView

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        UpdatePenjualanPresenter(requireContext(), this)

        val bundle = arguments
        if (bundle != null) {
            item = bundle.getParcelable(DATA_PENJUALAN)!!

            idObat = item.idObat.toInt()
            idSatuan = item.idSatuan.toInt()
            idObatMobile = item.idObatMobile.toInt()
            harga = item.harga.toInt()
            kategori = item.kategori

            update_nama_obat.setText(item.namaObat)
            update_golongan_obat.setText(item.namaGolongan)
            update_jenis_obat.setText(item.namaJenis)
            update_satuan_obat.setText(item.namaSatuan)
            update_harga_obat.setText(NumberFormat.getNumberInstance(Locale.US).format(harga))
            update_category_obat.setText(item.kategori)

        }

        loading = DialogHelper.showDialogLoading(requireContext())

        update_harga_obat.addTextChangedListener(hargaChange())
        btnCancelUpdatePenjualan.setOnClickListener(this)
        btnSaveUpdatePenjualan.setOnClickListener(this)

    }

    private fun hargaChange(): TextWatcher? {
        return object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun afterTextChanged(editable: Editable) {
                update_harga_obat.removeTextChangedListener(this)
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
                    update_harga_obat.setText(formattedString)
                    update_harga_obat.setSelection(update_harga_obat.text!!.length)
                } catch (nfe: NumberFormatException) {
                    nfe.printStackTrace()
                }
                update_harga_obat.addTextChangedListener(this)
            }
        }
    }

    override fun onUpdateError(code: Int, message: String) {
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

    override fun onUpdateSuccess(message: String) {
        this.dismiss()
        mCallback.onCloseFragmentUpdatePenjualan(true)
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
            btnCancelUpdatePenjualan.id -> this.dismiss()
            btnSaveUpdatePenjualan.id -> {
                if (handleInput()) {
                    harga = update_harga_obat.text.toString().trim { it <= ' ' }
                        .replace(",".toRegex(), "").toLong().toInt()

                    updatePenjualanBody = AddPenjualanBody.updatePenjualanBody(
                        harga!!, idObatMobile!!, idObat!!, idSatuan!!, kategori!!
                    )
                    presenter.updatePenjualan(updatePenjualanBody)
                }
            }
        }
    }

    private fun handleInput(): Boolean {
        if (update_harga_obat.text.isNullOrEmpty()) {
            update_harga_obat.error = getString(R.string.fill_data)
            update_harga_obat.requestFocus()
            return false
        }
        return true
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            mCallback = context as OnCLoseFragmentUpdatePenjualan
        } catch (e: ClassCastException) {
            Log.d(
                "Update Dialog",
                "Activity doesn't implement the OnCloseFragmentUpdatePenjualan interface"
            )
        }
    }

    interface OnCLoseFragmentUpdatePenjualan {
        fun onCloseFragmentUpdatePenjualan(b: Boolean)
    }

}