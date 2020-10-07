package id.owldevsoft.apotekseller.feature.penjualan.create

import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import id.owldevsoft.apotekseller.R
import id.owldevsoft.apotekseller.model.AddPenjualanBody
import id.owldevsoft.apotekseller.model.ObatResponse
import id.owldevsoft.apotekseller.model.Response
import id.owldevsoft.apotekseller.model.SatuanObatResponse
import id.owldevsoft.apotekseller.utils.DialogHelper
import id.owldevsoft.apotekseller.utils.PreferenceHelper
import kotlinx.android.synthetic.main.action_bar.*
import kotlinx.android.synthetic.main.fragment_add_penjualan.*
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class AddPenjualanFragment : Fragment(), AddPenjualanContract.View, View.OnClickListener {

    override lateinit var presenter: AddPenjualanContract.Presenter
    private var preferenceHelper: PreferenceHelper? = null
    private var loading: AlertDialog? = null
    private lateinit var addPenjualanBody: AddPenjualanBody

    private var adapterObat: ArrayAdapter<String>? = null
    private var listObat: ArrayList<String>? = null
    private var dataObat: ArrayList<ObatResponse.Data>? = null
    private var adapterSatuan: ArrayAdapter<String>? = null
    private var listSatuan: ArrayList<String>? = null
    private var dataSatuan: ArrayList<SatuanObatResponse.Data>? = null
    private var adapterKategori: ArrayAdapter<String>? = null

    private var idObat: Int? = null
    private var idSatuan: Int? = null
    private var idMember: Int? = null
    private var namaKategori: String? = null
    private var harga: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_penjualan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        back.visibility = View.GONE
        titlebar.text = "Tambah Penjualan"

        AddPenjualanPresenter(
            requireContext(),
            this
        )
        preferenceHelper = PreferenceHelper(requireContext())
        loading = DialogHelper.showDialogLoading(requireContext())

        presenter.getObat(preferenceHelper?.getUser()!!.data.member[0].idMembers)
        presenter.getSatuan(preferenceHelper?.getUser()!!.data.member[0].idMembers)
        initSpKategori()

        btnAddPenjualan.setOnClickListener(this)
        create_harga_obat.addTextChangedListener(hargaChange())

    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            btnAddPenjualan.id -> {
                if (handleInput()) {
                    harga = create_harga_obat.text.toString().trim { it <= ' ' }
                        .replace(",".toRegex(), "").toLong().toInt()
                    idMember = preferenceHelper?.getUser()!!.data.member[0].idMembers.toInt()
                    addPenjualanBody = AddPenjualanBody(harga!!, idMember!!, idObat!!, idSatuan!!, namaKategori!!)
                    presenter.addPenjualan(addPenjualanBody)
                }
            }
        }
    }

    private fun hargaChange(): TextWatcher? {
        return object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun afterTextChanged(editable: Editable) {
                create_harga_obat.removeTextChangedListener(this)
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
                    create_harga_obat.setText(formattedString)
                    create_harga_obat.setSelection(create_harga_obat.text!!.length)
                } catch (nfe: NumberFormatException) {
                    nfe.printStackTrace()
                }
                create_harga_obat.addTextChangedListener(this)
            }
        }
    }

    override fun onAddPenjualanSuccess(response: Response.ResponseUpdate) {
        sp_namaobat.setSelection(0)
        create_golongan_obat.setText("")
        create_jenis_obat.setText("")
        sp_satuanobat.setSelection(0)
        create_harga_obat.setText("")
        sp_kategori.setSelection(0)
        Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT).show()
    }

    override fun onObatSuccess(it: ArrayList<ObatResponse.Data>) {
        initSpObat(it)
    }

    override fun onSatuanSuccess(it: ArrayList<SatuanObatResponse.Data>) {
        initSpSatuan(it)
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

    override fun onProcess(boolean: Boolean) {
        if (boolean) {
            loadingProg.visibility = View.VISIBLE
            btnAddPenjualan.visibility = View.GONE
        } else {
            loadingProg.visibility = View.GONE
            btnAddPenjualan.visibility = View.VISIBLE
        }
    }

    override fun onProcessGet(boolean: Boolean) {
        if (boolean) {
            loading?.show()
        } else {
            loading?.dismiss()
        }
    }

    private fun initSpObat(it: ArrayList<ObatResponse.Data>) {
        listObat = ArrayList()
        adapterObat?.clear()
        listObat?.add("Pilih Nama Obat")
        dataObat = it
        it.forEachIndexed { index, data ->
            listObat?.add(data.namaObat)
        }
        adapterObat?.notifyDataSetChanged()
        adapterObat = ArrayAdapter(
            context!!, android.R.layout.simple_spinner_dropdown_item, listObat!!
        )
        sp_namaobat.adapter = adapterObat

        sp_namaobat.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p2 != 0) {
                    idObat = dataObat?.get(p2 - 1)?.idObat?.toInt()
                    create_golongan_obat.setText(dataObat?.get(p2 - 1)?.namaGolongan)
                    create_jenis_obat.setText(dataObat?.get(p2 - 1)?.namaJenis)
                } else {
                    idObat = null
                    create_golongan_obat.setText("")
                    create_jenis_obat.setText("")
                }
            }
        }

    }

    private fun initSpSatuan(it: ArrayList<SatuanObatResponse.Data>) {
        listSatuan = ArrayList()
        adapterSatuan?.clear()
        listSatuan?.add("Pilih Satuan")
        dataSatuan = it
        it.forEachIndexed { index, data ->
            listSatuan?.add(data.namaSatuan)
        }
        adapterSatuan?.notifyDataSetChanged()
        adapterSatuan = ArrayAdapter(
            context!!, android.R.layout.simple_spinner_dropdown_item, listSatuan!!
        )
        sp_satuanobat.adapter = adapterSatuan

        sp_satuanobat.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p2 != 0) {
                    idSatuan = dataSatuan?.get(p2 - 1)?.idSatuan?.toInt()
                } else {
                    idSatuan = null
                }
            }
        }

    }

    private fun initSpKategori() {
        adapterKategori?.clear()
        adapterKategori?.notifyDataSetChanged()
        adapterKategori = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            resources.getStringArray(R.array.kategori)
        )
        sp_kategori.adapter = adapterKategori

        sp_kategori.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p2 != 0) {
                    namaKategori = sp_kategori.selectedItem.toString()

                } else {
                    namaKategori = null
                }
            }
        }
    }

    private fun handleInput(): Boolean {
        if (sp_namaobat.selectedItemPosition == 0) {
            Toast.makeText(requireContext(), "Pilih Nama Obat", Toast.LENGTH_LONG).show()
            return false
        }
        if (sp_satuanobat.selectedItemPosition == 0) {
            Toast.makeText(requireContext(), "Pilih Satuan Obat", Toast.LENGTH_LONG).show()
            return false
        }
        if (create_harga_obat.text!!.isEmpty()) {
            create_harga_obat.error = getString(R.string.fill_data)
            create_harga_obat.requestFocus()
            return false
        }
        if (sp_kategori.selectedItemPosition == 0) {
            Toast.makeText(requireContext(), "Pilih Kategori Obat", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

}