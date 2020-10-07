package id.owldevsoft.apotekseller.feature.masterdata.jenisgolonganobat.read

import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import id.owldevsoft.apotekseller.R
import id.owldevsoft.apotekseller.adapter.JenisGolonganObatAdapter
import id.owldevsoft.apotekseller.feature.masterdata.jenisgolonganobat.create.CreateJenisGolonganObatFragment
import id.owldevsoft.apotekseller.feature.masterdata.jenisgolonganobat.delete.DeleteJenisGolonganObatFragment
import id.owldevsoft.apotekseller.feature.masterdata.jenisgolonganobat.update.UpdateJenisGolonganObatFragment
import id.owldevsoft.apotekseller.model.JenisObatResponse
import id.owldevsoft.apotekseller.utils.DialogHelper
import id.owldevsoft.apotekseller.utils.LogoutCallback
import id.owldevsoft.apotekseller.utils.LogoutHandle
import id.owldevsoft.apotekseller.utils.PreferenceHelper
import kotlinx.android.synthetic.main.action_bar.*
import kotlinx.android.synthetic.main.activity_jenis_golongan_obat.*
import java.util.*
import kotlin.collections.ArrayList

class JenisGolonganObatActivity : AppCompatActivity(),
    JenisGolonganObatContract.View, View.OnClickListener,
    CreateJenisGolonganObatFragment.OnCloseFragmentCreateJenisGolonganObat,
    DeleteJenisGolonganObatFragment.OnCloseFragmentDeleteJenisGolonganObat,
    UpdateJenisGolonganObatFragment.OnCLoseFragmentUpdateJenisGolonganObat {

    override lateinit var presenter: JenisGolonganObatContract.Presenter
    private var preferenceHelper: PreferenceHelper? = null
    private lateinit var logoutCallback: LogoutCallback

    private var loading: AlertDialog? = null
    private var jenisGolonganObatAdapter: JenisGolonganObatAdapter? = null
    private var listJenisGolonganObat: ArrayList<JenisObatResponse.Data>? = null
    private var searchListJenisGolonganObat: ArrayList<JenisObatResponse.Data>? = null

    private lateinit var createJenisGolonganObatFragment: CreateJenisGolonganObatFragment
    private lateinit var deleteJenisGolonganObatFragment: DeleteJenisGolonganObatFragment
    private lateinit var updateJenisGolonganObatFragment: UpdateJenisGolonganObatFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jenis_golongan_obat)

        JenisGolonganObatPresenter(this, this)
        preferenceHelper = PreferenceHelper(this)

        titlebar.text = "Jenis Golongan Obat"
        loading = DialogHelper.showDialogLoading(this)
        presenter.getJenisGolonganObat(preferenceHelper?.getUser()!!.data.member[0].idMembers)

        add_jenis_golongan_obat.setOnClickListener(this)
        back.setOnClickListener(this)
        iv_clear_jenis_golongan_obat.setOnClickListener(this)

        et_search_jenis_golongan_obat.addTextChangedListener(onSearchChange())

    }

    private fun onSearchChange(): TextWatcher? {
        return object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                searchListJenisGolonganObat = ArrayList()
                if (p0?.isEmpty()!!) {
                    searchListJenisGolonganObat = listJenisGolonganObat
                    iv_clear_jenis_golongan_obat.visibility = View.GONE
                } else {
                    iv_clear_jenis_golongan_obat.visibility = View.VISIBLE
                    searchListJenisGolonganObat = filterJenisGolonganObat(p0.toString())
                }

                val clickAction = object : JenisGolonganObatAdapter.OnClickActionJenisGolonganObat {

                    override fun onCLickUpdate(position: JenisObatResponse.Data?) {
                        updateJenisGolonganObatFragment = UpdateJenisGolonganObatFragment()
                        val bundle = Bundle()
                        bundle.putString(
                            UpdateJenisGolonganObatFragment.ID_JENIS_GOLONGAN_OBAT,
                            position?.idJenisObat
                        )
                        bundle.putString(
                            UpdateJenisGolonganObatFragment.NAMA_JENIS_GOLONGAN_OBAT,
                            position?.namaJenis
                        )
                        updateJenisGolonganObatFragment.arguments = bundle
                        updateJenisGolonganObatFragment.setStyle(
                            DialogFragment.STYLE_NORMAL,
                            R.style.DialogFragmentTheme
                        )
                        updateJenisGolonganObatFragment.show(
                            supportFragmentManager.beginTransaction(),
                            "Update Jenis Golongan Obat"
                        )
                    }

                    override fun onClickDelete(position: JenisObatResponse.Data?) {
                        deleteJenisGolonganObatFragment = DeleteJenisGolonganObatFragment()
                        val bundle = Bundle()
                        bundle.putString(
                            DeleteJenisGolonganObatFragment.ID_JENIS_GOLONGAN_OBAT,
                            position?.idJenisObat
                        )
                        bundle.putString(
                            DeleteJenisGolonganObatFragment.NAMA_JENIS_GOLONGAN_OBAT,
                            position?.namaJenis
                        )
                        deleteJenisGolonganObatFragment.arguments = bundle
                        deleteJenisGolonganObatFragment.setStyle(
                            DialogFragment.STYLE_NORMAL,
                            R.style.DialogFragmentTheme
                        )
                        deleteJenisGolonganObatFragment.show(
                            supportFragmentManager.beginTransaction(),
                            "Delete Jenis Golongan Obat"
                        )
                    }

                }

                jenisGolonganObatAdapter =
                    JenisGolonganObatAdapter(searchListJenisGolonganObat, baseContext, clickAction)
                rv_master_data_jenis_golongan_obat.adapter = jenisGolonganObatAdapter
                rv_master_data_jenis_golongan_obat.layoutManager =
                    LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL, false)

            }

        }
    }

    override fun onJenisGolonganError(code: Int, message: String) {
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

    override fun onJenisGolonganSuccess(it: ArrayList<JenisObatResponse.Data>) {
        this.listJenisGolonganObat = it
        val clickAction = object : JenisGolonganObatAdapter.OnClickActionJenisGolonganObat {
            override fun onCLickUpdate(position: JenisObatResponse.Data?) {
                updateJenisGolonganObatFragment = UpdateJenisGolonganObatFragment()
                val bundle = Bundle()
                bundle.putString(
                    UpdateJenisGolonganObatFragment.ID_JENIS_GOLONGAN_OBAT,
                    position?.idJenisObat
                )
                bundle.putString(
                    UpdateJenisGolonganObatFragment.NAMA_JENIS_GOLONGAN_OBAT,
                    position?.namaJenis
                )
                updateJenisGolonganObatFragment.arguments = bundle
                updateJenisGolonganObatFragment.setStyle(
                    DialogFragment.STYLE_NORMAL,
                    R.style.DialogFragmentTheme
                )
                updateJenisGolonganObatFragment.show(
                    supportFragmentManager.beginTransaction(),
                    "Update Jenis Golongan Obat"
                )
            }

            override fun onClickDelete(position: JenisObatResponse.Data?) {
                deleteJenisGolonganObatFragment = DeleteJenisGolonganObatFragment()
                val bundle = Bundle()
                bundle.putString(
                    DeleteJenisGolonganObatFragment.ID_JENIS_GOLONGAN_OBAT,
                    position?.idJenisObat
                )
                bundle.putString(
                    DeleteJenisGolonganObatFragment.NAMA_JENIS_GOLONGAN_OBAT,
                    position?.namaJenis
                )
                deleteJenisGolonganObatFragment.arguments = bundle
                deleteJenisGolonganObatFragment.setStyle(
                    DialogFragment.STYLE_NORMAL,
                    R.style.DialogFragmentTheme
                )
                deleteJenisGolonganObatFragment.show(
                    supportFragmentManager.beginTransaction(),
                    "Delete Jenis Golongan Obat"
                )
            }

        }
        jenisGolonganObatAdapter = JenisGolonganObatAdapter(it, baseContext, clickAction)
        rv_master_data_jenis_golongan_obat.adapter = jenisGolonganObatAdapter
        rv_master_data_jenis_golongan_obat.layoutManager =
            LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL, false)
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
            add_jenis_golongan_obat.id -> {
                createJenisGolonganObatFragment = CreateJenisGolonganObatFragment()
                createJenisGolonganObatFragment.setStyle(
                    DialogFragment.STYLE_NORMAL,
                    R.style.DialogFragmentTheme
                )
                createJenisGolonganObatFragment.show(
                    supportFragmentManager.beginTransaction(),
                    "Create Produsen"
                )
            }
            back.id -> finish()
            iv_clear_jenis_golongan_obat.id -> et_search_jenis_golongan_obat.setText("")
        }
    }

    private fun filterJenisGolonganObat(search: String): ArrayList<JenisObatResponse.Data>? {
        val result: ArrayList<JenisObatResponse.Data> = ArrayList()
        for (i in listJenisGolonganObat?.indices!!) {
            if (listJenisGolonganObat!!.get(i).namaJenis.toLowerCase(Locale.ROOT).contains(
                    search.toLowerCase(
                        Locale.ROOT
                    )
                )
            ) {
                result.add(listJenisGolonganObat!![i])
            }
        }
        return result
    }

    override fun onCloseFragmentCreateJenisGolonganObat(b: Boolean) {
        presenter.getJenisGolonganObat(preferenceHelper?.getUser()!!.data.member[0].idMembers)
        et_search_jenis_golongan_obat.setText("")
    }

    override fun onCloseFragmentDeleteJenisGolonganObat(b: Boolean) {
        presenter.getJenisGolonganObat(preferenceHelper?.getUser()!!.data.member[0].idMembers)
        et_search_jenis_golongan_obat.setText("")
    }

    override fun onCloseFragmentUpdateJenisGolonganObat(b: Boolean) {
        presenter.getJenisGolonganObat(preferenceHelper?.getUser()!!.data.member[0].idMembers)
        et_search_jenis_golongan_obat.setText("")
    }

}