package id.owldevsoft.apotekseller.feature.masterproduk.update

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import id.owldevsoft.apotekseller.BuildConfig
import id.owldevsoft.apotekseller.R
import id.owldevsoft.apotekseller.model.*
import id.owldevsoft.apotekseller.utils.*
import kotlinx.android.synthetic.main.action_bar.*
import kotlinx.android.synthetic.main.activity_tambah_data_obat.*
import kotlinx.android.synthetic.main.activity_update_data_obat.*
import kotlinx.android.synthetic.main.activity_update_data_obat.loadingProg
import kotlinx.android.synthetic.main.activity_update_data_obat.sp_golongan
import kotlinx.android.synthetic.main.activity_update_data_obat.sp_jenis
import kotlinx.android.synthetic.main.activity_update_data_obat.sp_merk
import kotlinx.android.synthetic.main.activity_update_data_obat.sp_subgolongan
import kotlinx.android.synthetic.main.activity_update_data_obat.sp_tipe

class UpdateDataObatActivity : AppCompatActivity(), UpdateDataObatContract.View, View.OnClickListener {

    override lateinit var presenter: UpdateDataObatContract.Presenter
    private var preferenceHelper: PreferenceHelper? = null
    private var loading: AlertDialog? = null
    private lateinit var updatDataObatBody: DataObatBody.DataObatUpdateBody

    private var adapterGolongan: ArrayAdapter<String>? = null
    private var listGolongan: ArrayList<String>? = null
    private var dataGolongan: ArrayList<JenisObatResponse.Data>? = null
    private var adapterSubGolongan: ArrayAdapter<String>? = null
    private var listSubGolongan: ArrayList<String>? = null
    private var dataSubGolongan: ArrayList<SubGolonganObatResponse.Data>? = null
    private var adapterMerk: ArrayAdapter<String>? = null
    private var listMerk: ArrayList<String>? = null
    private var dataMerk: ArrayList<ProdusenResponse.Data>? = null
    private var adapterJenis: ArrayAdapter<String>? = null
    private var listJenis: ArrayList<String>? = null
    private var dataJenis: ArrayList<GolonganObatResponse.Data>? = null
    private var adapterTipe: ArrayAdapter<String>? = null

    private var idObat: String? = null
    private var idJenisGolongan: String? = null
    private var idSubGolongan: String? = null
    private var idMerk: String? = null
    private var idGolongan: String? = null
    private var namaTipe: String? = null
    private var photo: String? = null
    private var flgPhoto: String = "0"

    private var posJenisGol: Int? = null
    private var posSubGol: Int? = null
    private var posMerk: Int? = null
    private var posGol: Int? = null
    private var posTipe: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_data_obat)

        UpdateDataObatPresenter(this, this)
        preferenceHelper = PreferenceHelper(this)

        titlebar.text = "Ubah Data Obat"

        val extra = intent.getStringExtra("data")
        val data = Gson().fromJson(extra, ObatResponse.Data::class.java)

        update_nama_obat.setText(data.namaObat)
        update_kode_obat.setText(data.kodeObat)
        update_barcode_obat.setText(data.barcodeObat)
        update_stok.setText(data.stokTotal)
        update_indikasi.setText(data.indikasi)
        update_dosis.setText(data.dosis)
        update_komposisi.setText(data.komposisi)
        update_deskripsi.setText(data.deskripsi)
        Picasso.get().load("${BuildConfig.BASE_URL}${data.postImage}")
            .placeholder(R.drawable.ic_baseline_camera_alt_24).into(photo_image_update)
        idObat = data.idObat
        idJenisGolongan = data.idJenisObat
        idSubGolongan = data.idSubGolongan
        idMerk = data.idMerk
        idGolongan = data.idGolongan
        namaTipe = data.tipe
        photo = data.postImage

        loading = DialogHelper.showDialogLoading(this)
        presenter.getJenisGolonganObat(preferenceHelper?.getUser()!!.data.member[0].idMembers)
        presenter.getSubGolongan(preferenceHelper?.getUser()!!.data.member[0].idMembers)
        presenter.getProdusen(preferenceHelper?.getUser()!!.data.member[0].idMembers)
        presenter.getGolongan()

        initSpTipe()

        back.setOnClickListener(this)
        btnUpdateObat.setOnClickListener(this)
        photo_image_update.setOnClickListener(this)

    }

    override fun onJenisGolonganSuccess(it: ArrayList<JenisObatResponse.Data>) {
        initSpGolongan(it)
    }

    override fun onSubGolonganSuccess(it: ArrayList<SubGolonganObatResponse.Data>) {
        initSpSubGolongan(it)
    }

    override fun onMerkSuccess(it: ArrayList<ProdusenResponse.Data>) {
        initSpMerk(it)
    }

    override fun onGolonganSuccess(it: ArrayList<GolonganObatResponse.Data>) {
        initSpJenis(it)
    }

    override fun onUpdateObatSuccess(message: String) {
        finish()
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onError(code: Int, message: String) {
        DialogHelper.showWarnDialog(
            this,
            message,
            getString(R.string.ans_mes_ok),
            false,
            object : DialogHelper.Positive {
                override fun positiveButton(dialog: DialogInterface, id: Int) {
                    dialog.dismiss()
                }
            })
    }

    override fun onProcess(boolean: Boolean) {
        if (boolean) {
            loadingProg.visibility = View.VISIBLE
            btnUpdateObat.visibility = View.GONE
        } else {
            loadingProg.visibility = View.GONE
            btnUpdateObat.visibility = View.VISIBLE
        }
    }

    private fun initSpGolongan(it: ArrayList<JenisObatResponse.Data>) {
        listGolongan = ArrayList()
        adapterGolongan?.clear()
        listGolongan?.add("Pilih Golongan")
        dataGolongan = it
        it.forEachIndexed { index, data ->
            if (data.idJenisObat == idJenisGolongan){
                posJenisGol = index + 1
            }
            listGolongan?.add(data.namaJenis)
        }
        adapterGolongan?.notifyDataSetChanged()
        adapterGolongan = ArrayAdapter(
            this, android.R.layout.simple_spinner_dropdown_item, listGolongan!!
        )
        sp_golongan.adapter = adapterGolongan

        sp_golongan.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p2 != 0) {
                    idJenisGolongan = dataGolongan?.get(p2 - 1)?.idJenisObat

                } else {
                    idJenisGolongan = null
                }
            }
        }
        if (posJenisGol != null){
            sp_golongan.setSelection(posJenisGol!!)
        }
    }

    private fun initSpSubGolongan(it: ArrayList<SubGolonganObatResponse.Data>) {
        listSubGolongan = ArrayList()
        adapterSubGolongan?.clear()
        listSubGolongan?.add("Pilih Sub Golongan")
        dataSubGolongan = it
        it.forEachIndexed { index, data ->
            if (data.idSubGolongan == idSubGolongan){
                posSubGol = index + 1
            }
            listSubGolongan?.add(data.namaSubGolongan)
        }
        adapterSubGolongan?.notifyDataSetChanged()
        adapterSubGolongan = ArrayAdapter(
            this, android.R.layout.simple_spinner_dropdown_item, listSubGolongan!!
        )
        sp_subgolongan.adapter = adapterSubGolongan

        sp_subgolongan.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p2 != 0) {
                    idSubGolongan = dataSubGolongan?.get(p2 - 1)?.idSubGolongan

                } else {
                    idSubGolongan = null
                }
            }
        }
        if (posSubGol != null){
            sp_subgolongan.setSelection(posSubGol!!)
        }
    }

    private fun initSpMerk(it: ArrayList<ProdusenResponse.Data>) {
        listMerk = ArrayList()
        adapterMerk?.clear()
        listMerk?.add("Pilih Merk")
        dataMerk = it
        it.forEachIndexed { index, data ->
            if (data.idMerk == idMerk){
                posMerk = index + 1
            }
            listMerk?.add(data.namaMerk)
        }
        adapterMerk?.notifyDataSetChanged()
        adapterMerk = ArrayAdapter(
            this, android.R.layout.simple_spinner_dropdown_item, listMerk!!
        )
        sp_merk.adapter = adapterMerk

        sp_merk.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p2 != 0) {
                    idMerk = dataMerk?.get(p2 - 1)?.idMerk

                } else {
                    idMerk = null
                }
            }
        }
        if (posMerk != null){
            sp_merk.setSelection(posMerk!!)
        }
    }

    private fun initSpJenis(it: ArrayList<GolonganObatResponse.Data>) {
        listJenis = ArrayList()
        adapterJenis?.clear()
        listJenis?.add("Pilih Jenis")
        dataJenis = it
        it.forEachIndexed { index, data ->
            if (data.idGolongan == idGolongan){
                posGol = index + 1
            }
            listJenis?.add(data.namaGolongan)
        }
        adapterJenis?.notifyDataSetChanged()
        adapterJenis = ArrayAdapter(
            this, android.R.layout.simple_spinner_dropdown_item, listJenis!!
        )
        sp_jenis.adapter = adapterJenis

        sp_jenis.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p2 != 0) {
                    idGolongan = dataJenis?.get(p2 - 1)?.idGolongan

                } else {
                    idGolongan = null
                }
            }
        }
        if (posGol != null){
            sp_jenis.setSelection(posGol!!)
        }
    }

    private fun initSpTipe() {
        adapterTipe?.clear()
        adapterTipe?.notifyDataSetChanged()
        adapterTipe = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            resources.getStringArray(R.array.tipe_obat)
        )
        sp_tipe.adapter = adapterTipe
        posTipe = adapterTipe!!.getPosition(namaTipe)
        sp_tipe.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p2 != 0) {
                    namaTipe = sp_tipe.selectedItem.toString()

                } else {
                    namaTipe = null
                }
            }
        }
        sp_tipe.setSelection(posTipe!!)
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            back.id -> finish()
            btnUpdateObat.id -> {
                if (handleInput()) {
                    val namaObat = update_nama_obat.text.toString()
                    val kode = update_kode_obat.text.toString()
                    val barcode = update_barcode_obat.text.toString()
                    val idJenisObat = idJenisGolongan
                    val idGolongan = idGolongan
                    val idSubGolongan = idSubGolongan
                    val idMerk = idMerk
                    val tipe = namaTipe
                    val indikasi = update_indikasi.text.toString()
                    val photo = photo
                    val deskripsi = update_deskripsi.text.toString()
                    val komposisi = update_komposisi.text.toString()
                    val dosis = update_dosis.text.toString()
                    val stok = update_stok.text.toString()
                    val idObat = idObat

                    updatDataObatBody = DataObatBody.DataObatUpdateBody(
                        namaObat,
                        kode,
                        barcode,
                        idJenisObat!!,
                        idGolongan!!,
                        idSubGolongan!!,
                        idMerk!!,
                        tipe!!,
                        indikasi,
                        photo!!,
                        deskripsi,
                        komposisi,
                        dosis,
                        stok,
                        idObat!!,
                        flgPhoto
                    )
                    presenter.postUpdateObat(updatDataObatBody)
                }
            }
            photo_image_update.id -> getImageFromGallery()
        }
    }

    private fun handleInput(): Boolean {
        if (update_nama_obat.text!!.isEmpty()) {
            update_nama_obat.error = getString(R.string.fill_data)
            update_nama_obat.requestFocus()
            return false
        }
        if (update_kode_obat.text!!.isEmpty()) {
            update_kode_obat.error = getString(R.string.fill_data)
            update_kode_obat.requestFocus()
            return false
        }
        if (update_barcode_obat.text!!.isEmpty()) {
            update_barcode_obat.error = getString(R.string.fill_data)
            update_barcode_obat.requestFocus()
            return false
        }
        if (sp_golongan.selectedItemPosition == 0) {
            Toast.makeText(baseContext, "Pilih Golongan Obat", Toast.LENGTH_LONG).show()
            return false
        }
        if (sp_subgolongan.selectedItemPosition == 0) {
            Toast.makeText(baseContext, "Pilih Sub Golongan Obat", Toast.LENGTH_LONG).show()
            return false
        }
        if (sp_merk.selectedItemPosition == 0) {
            Toast.makeText(baseContext, "Pilih Merk Obat", Toast.LENGTH_LONG).show()
            return false
        }
        if (sp_jenis.selectedItemPosition == 0) {
            Toast.makeText(baseContext, "Pilih Jenis Obat", Toast.LENGTH_LONG).show()
            return false
        }
        if (sp_tipe.selectedItemPosition == 0) {
            Toast.makeText(baseContext, "Pilih Tipe Obat", Toast.LENGTH_LONG).show()
            return false
        }
        if (update_stok.text!!.isEmpty()) {
            update_stok.error = getString(R.string.fill_data)
            update_stok.requestFocus()
            return false
        }
        if (update_indikasi.text!!.isEmpty()) {
            update_indikasi.error = getString(R.string.fill_data)
            update_indikasi.requestFocus()
            return false
        }
        if (update_dosis.text!!.isEmpty()) {
            update_dosis.error = getString(R.string.fill_data)
            update_dosis.requestFocus()
            return false
        }
        if (update_komposisi.text!!.isEmpty()) {
            update_komposisi.error = getString(R.string.fill_data)
            update_komposisi.requestFocus()
            return false
        }
        if (update_deskripsi.text!!.isEmpty()) {
            update_deskripsi.error = getString(R.string.fill_data)
            update_deskripsi.requestFocus()
            return false
        }
        if (photo.isNullOrEmpty()) {
            Toast.makeText(this, "Photo tidak boleh kososng", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun getImageFromGallery() {

        val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        pickIntent.type = "image/*"
        startActivityForResult(
            Intent.createChooser(pickIntent, getString(R.string.dialog_title_pick_picture)),
            Constants.CODE_TARGET_PHOTO
        )

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                Constants.CODE_TARGET_PHOTO -> {
                    Picasso.get().load(data?.data).resize(1000, 500).into(photo_image_update)
//                    sia_image.setImageURI(data?.data)
                    processPicture(data?.data)
                    flgPhoto = "1"
//                    processPicture(data?.data)
                }
            }
        }
    }

    fun processPicture(data: Uri?) {
        val image = ImCompressor().compress(
            ImageHelper.getPathImage(this, data!!),
            this.getExternalFilesDir(
                Environment.DIRECTORY_PICTURES
            )?.absolutePath
        )
        photo = image
    }

}