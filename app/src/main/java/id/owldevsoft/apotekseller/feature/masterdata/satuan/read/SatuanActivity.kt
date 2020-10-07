package id.owldevsoft.apotekseller.feature.masterdata.satuan.read

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
import id.owldevsoft.apotekseller.adapter.SatuanAdapter
import id.owldevsoft.apotekseller.feature.masterdata.satuan.create.CreateSatuanFragment
import id.owldevsoft.apotekseller.feature.masterdata.satuan.delete.DeleteSatuanFragment
import id.owldevsoft.apotekseller.feature.masterdata.satuan.update.UpdateSatuanFragment
import id.owldevsoft.apotekseller.model.SatuanObatResponse
import id.owldevsoft.apotekseller.utils.DialogHelper
import id.owldevsoft.apotekseller.utils.PreferenceHelper
import kotlinx.android.synthetic.main.action_bar.*
import kotlinx.android.synthetic.main.activity_satuan.*
import java.util.*
import kotlin.collections.ArrayList

class SatuanActivity : AppCompatActivity(),
    SatuanContract.View, View.OnClickListener,
    UpdateSatuanFragment.OnCLoseFragmentUpdateSatuan,
    CreateSatuanFragment.OnCloseFragmentCreateSatuan,
    DeleteSatuanFragment.OnCloseFragmentDeleteSatuan {

    override lateinit var presenter: SatuanContract.Presenter
    private var preferenceHelper: PreferenceHelper? = null

    private var loading: AlertDialog? = null
    private var satuanAdapter: SatuanAdapter? = null
    private var listSatuan: ArrayList<SatuanObatResponse.Data>? = null
    private var searchListSatuan: ArrayList<SatuanObatResponse.Data>? = null

    private lateinit var updateSatuanFragment: UpdateSatuanFragment
    private lateinit var createSatuanFragment: CreateSatuanFragment
    private lateinit var deleteSatuanFragment: DeleteSatuanFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_satuan)

        SatuanPresenter(
            this,
            this
        )
        preferenceHelper = PreferenceHelper(this)

        titlebar.text = "Satuan"
        loading = DialogHelper.showDialogLoading(this)
        presenter.getSatuan(preferenceHelper?.getUser()!!.data.member[0].idMembers)

        add_satuan_obat.setOnClickListener(this)
        back.setOnClickListener(this)
        iv_clear.setOnClickListener(this)

        et_search_satuan.addTextChangedListener(onSearchChange())

    }

    private fun onSearchChange(): TextWatcher? {
        return object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                searchListSatuan = ArrayList()
                if (p0?.isEmpty()!!) {
                    searchListSatuan = listSatuan
                    iv_clear.visibility = View.GONE
                } else {
                    iv_clear.visibility = View.VISIBLE
                    searchListSatuan = filterSatuan(p0.toString())
                }

                val clickAction = object : SatuanAdapter.OnClickAction {

                    override fun onCLickUpdate(position: SatuanObatResponse.Data?) {
                        updateSatuanFragment = UpdateSatuanFragment()
                        val bundle = Bundle()
                        bundle.putString(UpdateSatuanFragment.ID_SATUAN, position?.idSatuan)
                        updateSatuanFragment.arguments = bundle
                        updateSatuanFragment.setStyle(
                            DialogFragment.STYLE_NORMAL,
                            R.style.DialogFragmentTheme
                        )
                        updateSatuanFragment.show(
                            supportFragmentManager.beginTransaction(),
                            "Update Satuan"
                        )
                    }

                    override fun onClickDelete(position: SatuanObatResponse.Data?) {
                        deleteSatuanFragment = DeleteSatuanFragment()
                        val bundle = Bundle()
                        bundle.putString(DeleteSatuanFragment.ID_SATUAN, position?.idSatuan)
                        bundle.putString(DeleteSatuanFragment.NAMA_SATUAN, position?.namaSatuan)
                        deleteSatuanFragment.arguments = bundle
                        deleteSatuanFragment.setStyle(
                            DialogFragment.STYLE_NORMAL,
                            R.style.DialogFragmentTheme
                        )
                        deleteSatuanFragment.show(
                            supportFragmentManager.beginTransaction(),
                            "Delete Satuan"
                        )
                    }

                }

                satuanAdapter = SatuanAdapter(searchListSatuan, baseContext, clickAction)
                rv_master_data_satuan.adapter = satuanAdapter
                rv_master_data_satuan.layoutManager =
                    LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL, false)

            }

        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            add_satuan_obat.id -> {
                createSatuanFragment = CreateSatuanFragment()
                createSatuanFragment.setStyle(
                    DialogFragment.STYLE_NORMAL,
                    R.style.DialogFragmentTheme
                )
                createSatuanFragment.show(
                    supportFragmentManager.beginTransaction(),
                    "Create Satuan"
                )
            }
            back.id -> finish()
            iv_clear.id -> et_search_satuan.setText("")
        }
    }

    override fun onSatuanError(code: Int, message: String) {
        DialogHelper.showWarnDialog(
            this,
            getString(R.string.err_mes, message),
            getString(R.string.ans_mes_ok),
            false,
            object : DialogHelper.Positive {
                override fun positiveButton(dialog: DialogInterface, id: Int) {
                    dialog.dismiss()
                }
            })
    }

    override fun onSatuanSuccess(it: ArrayList<SatuanObatResponse.Data>) {

        this.listSatuan = it

        val clickAction = object : SatuanAdapter.OnClickAction {

            override fun onCLickUpdate(position: SatuanObatResponse.Data?) {
                updateSatuanFragment = UpdateSatuanFragment()
                val bundle = Bundle()
                bundle.putString(UpdateSatuanFragment.ID_SATUAN, position?.idSatuan)
                bundle.putString(UpdateSatuanFragment.NAMA_SATUAN, position?.namaSatuan)
                updateSatuanFragment.arguments = bundle
                updateSatuanFragment.setStyle(
                    DialogFragment.STYLE_NORMAL,
                    R.style.DialogFragmentTheme
                )
                updateSatuanFragment.show(
                    supportFragmentManager.beginTransaction(),
                    "Update Satuan"
                )
            }

            override fun onClickDelete(position: SatuanObatResponse.Data?) {
                deleteSatuanFragment = DeleteSatuanFragment()
                val bundle = Bundle()
                bundle.putString(DeleteSatuanFragment.ID_SATUAN, position?.idSatuan)
                bundle.putString(DeleteSatuanFragment.NAMA_SATUAN, position?.namaSatuan)
                deleteSatuanFragment.arguments = bundle
                deleteSatuanFragment.setStyle(
                    DialogFragment.STYLE_NORMAL,
                    R.style.DialogFragmentTheme
                )
                deleteSatuanFragment.show(
                    supportFragmentManager.beginTransaction(),
                    "Delete Satuan"
                )
            }

        }
        satuanAdapter = SatuanAdapter(it, this, clickAction)
        rv_master_data_satuan.adapter = satuanAdapter
        rv_master_data_satuan.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    override fun onProcess(boolean: Boolean) {
        if (boolean) {
            loading?.show()
        } else {
            loading?.dismiss()
        }
    }

    private fun filterSatuan(search: String): ArrayList<SatuanObatResponse.Data>? {
        val result: ArrayList<SatuanObatResponse.Data> = ArrayList()
        for (i in listSatuan?.indices!!) {
            if (listSatuan!!.get(i).namaSatuan.toLowerCase(Locale.ROOT).contains(
                    search.toLowerCase(
                        Locale.ROOT
                    )
                )
            ) {
                result.add(listSatuan!![i])
            }
        }
        return result
    }

    override fun onCloseFragmentUpdateSatuan(b: Boolean) {
        presenter.getSatuan(preferenceHelper?.getUser()!!.data.member[0].idMembers)
        et_search_satuan.setText("")
    }

    override fun onCloseFragmentCreateSatuan(b: Boolean) {
        presenter.getSatuan(preferenceHelper?.getUser()!!.data.member[0].idMembers)
        et_search_satuan.setText("")
    }

    override fun onCloseFragmentDeleteSatuan(b: Boolean) {
        presenter.getSatuan(preferenceHelper?.getUser()!!.data.member[0].idMembers)
        et_search_satuan.setText("")
    }

}