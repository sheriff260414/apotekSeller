package id.owldevsoft.apotekseller.feature.masterproduk.create

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
import androidx.core.net.toUri
import com.squareup.picasso.Picasso
import id.owldevsoft.apotekseller.R
import id.owldevsoft.apotekseller.model.*
import id.owldevsoft.apotekseller.utils.*
import kotlinx.android.synthetic.main.action_bar.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_tambah_data_obat.*
import kotlinx.android.synthetic.main.activity_tambah_data_obat.loadingProg
import kotlinx.android.synthetic.main.fragment_profil.*
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

class TambahDataObatActivity : AppCompatActivity(), View.OnClickListener,
    TambahDataObatContract.View {

    override lateinit var presenter: TambahDataObatContract.Presenter
    private var preferenceHelper: PreferenceHelper? = null
    private var loading: AlertDialog? = null
    private lateinit var dataObatBody: DataObatBody

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
//    private var listTipe: ArrayList<String>? = null
//    private var dataGolongan: ArrayList<JenisObatResponse.Data>? = null

    private var idJenisGolongan: String? = null
    private var idSubGolongan: String? = null
    private var idMerk: String? = null
    private var idGolongan: String? = null
    private var namaTipe: String? = null
    private var photo: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_data_obat)

        TambahDataObatPresenter(
            this,
            this
        )
        preferenceHelper = PreferenceHelper(this)

        titlebar.text = "Tambah Data Obat"
        back.setOnClickListener(this)
        btnTmbahObat.setOnClickListener(this)
        photo_image.setOnClickListener(this)

        loading = DialogHelper.showDialogLoading(this)
        presenter.getJenisGolonganObat(preferenceHelper?.getUser()!!.data.member[0].idMembers)
        presenter.getSubGolongan(preferenceHelper?.getUser()!!.data.member[0].idMembers)
        presenter.getProdusen(preferenceHelper?.getUser()!!.data.member[0].idMembers)
        presenter.getGolongan()

        initSpTipe()

    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            back.id -> finish()
            btnTmbahObat.id -> {
                if (handleInput()) {
                    val namaObat = create_nama_obat.text.toString()
                    val kode = create_kode_obat.text.toString()
                    val barcode = create_barcode_obat.text.toString()
                    val idJenisObat = idJenisGolongan
                    val idGolongan = idGolongan
                    val idSubGolongan = idSubGolongan
                    val idMerk = idMerk
                    val tipe = namaTipe
                    val indikasi = create_indikasi.text.toString()
                    val photo = photo
                    val deskripsi = create_deskripsi.text.toString()
                    val komposisi = create_komposisi.text.toString()
                    val dosis = create_dosis.text.toString()
                    val stok = create_stok.text.toString()
                    val idMember = preferenceHelper?.getUser()!!.data.member[0].idMembers

                    dataObatBody = DataObatBody(
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
                        idMember
                    )
                    presenter.postObat(dataObatBody)

                }
            }
            photo_image.id -> getImageFromGallery()
        }
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

    override fun onCreateObatSuccess(message: String) {
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
            btnTmbahObat.visibility = View.GONE
        } else {
            loadingProg.visibility = View.GONE
            btnTmbahObat.visibility = View.VISIBLE
        }
    }

    private fun initSpGolongan(it: ArrayList<JenisObatResponse.Data>) {
        listGolongan = ArrayList()
        adapterGolongan?.clear()
        listGolongan?.add("Pilih Golongan")
        dataGolongan = it
        it.forEachIndexed { index, data ->
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
    }

    private fun initSpSubGolongan(it: ArrayList<SubGolonganObatResponse.Data>) {
        listSubGolongan = ArrayList()
        adapterSubGolongan?.clear()
        listSubGolongan?.add("Pilih Sub Golongan")
        dataSubGolongan = it
        it.forEachIndexed { index, data ->
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
    }

    private fun initSpMerk(it: ArrayList<ProdusenResponse.Data>) {
        listMerk = ArrayList()
        adapterMerk?.clear()
        listMerk?.add("Pilih Merk")
        dataMerk = it
        it.forEachIndexed { index, data ->
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
    }

    private fun initSpJenis(it: ArrayList<GolonganObatResponse.Data>) {
        listJenis = ArrayList()
        adapterJenis?.clear()
        listJenis?.add("Pilih Jenis")
        dataJenis = it
        it.forEachIndexed { index, data ->
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
    }

    private fun handleInput(): Boolean {
        if (create_nama_obat.text!!.isEmpty()) {
            create_nama_obat.error = getString(R.string.fill_data)
            create_nama_obat.requestFocus()
            return false
        }
        if (create_kode_obat.text!!.isEmpty()) {
            create_kode_obat.error = getString(R.string.fill_data)
            create_kode_obat.requestFocus()
            return false
        }
        if (create_barcode_obat.text!!.isEmpty()) {
            create_barcode_obat.error = getString(R.string.fill_data)
            create_barcode_obat.requestFocus()
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
        if (create_stok.text!!.isEmpty()) {
            create_stok.error = getString(R.string.fill_data)
            create_stok.requestFocus()
            return false
        }
        if (create_indikasi.text!!.isEmpty()) {
            create_indikasi.error = getString(R.string.fill_data)
            create_indikasi.requestFocus()
            return false
        }
        if (create_dosis.text!!.isEmpty()) {
            create_dosis.error = getString(R.string.fill_data)
            create_dosis.requestFocus()
            return false
        }
        if (create_komposisi.text!!.isEmpty()) {
            create_komposisi.error = getString(R.string.fill_data)
            create_komposisi.requestFocus()
            return false
        }
        if (create_deskripsi.text!!.isEmpty()) {
            create_deskripsi.error = getString(R.string.fill_data)
            create_deskripsi.requestFocus()
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
                    Picasso.get().load(data?.data).resize(1000, 500).into(photo_image)
//                    sia_image.setImageURI(data?.data)
                    processPicture(data?.data)
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