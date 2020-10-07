package id.owldevsoft.apotekseller.feature.masterdata.subgolonganobat.read

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
import id.owldevsoft.apotekseller.adapter.SubGolonganObatAdapter
import id.owldevsoft.apotekseller.feature.masterdata.subgolonganobat.create.CreateSubGolonganObatFragment
import id.owldevsoft.apotekseller.feature.masterdata.subgolonganobat.delete.DeleteSubGolonganObatFragment
import id.owldevsoft.apotekseller.feature.masterdata.subgolonganobat.update.UpdateSubGolonganObatFragment
import id.owldevsoft.apotekseller.model.SubGolonganObatResponse
import id.owldevsoft.apotekseller.utils.DialogHelper
import id.owldevsoft.apotekseller.utils.PreferenceHelper
import kotlinx.android.synthetic.main.action_bar.*
import kotlinx.android.synthetic.main.activity_sub_golongan_obat.*
import java.util.*
import kotlin.collections.ArrayList

class SubGolonganObatActivity : AppCompatActivity(),
    SubGolonganObatContract.View,
    View.OnClickListener, CreateSubGolonganObatFragment.OnCloseFragmentCreateSubGolonganObat,
    UpdateSubGolonganObatFragment.OnCLoseFragmentUpdateSubGolonganObat,
    DeleteSubGolonganObatFragment.OnCloseFragmentDeleteSubGolonganObat {

    override lateinit var presenter: SubGolonganObatContract.Presenter
    private var preferenceHelper: PreferenceHelper? = null

    private var loading: AlertDialog? = null
    private var subGolonganObatAdapter: SubGolonganObatAdapter? = null
    private var listSubGolonganObat: ArrayList<SubGolonganObatResponse.Data>? = null
    private var searchListSubGolonganObat: ArrayList<SubGolonganObatResponse.Data>? = null

    private lateinit var createSubGolonganObatFragment: CreateSubGolonganObatFragment
    private lateinit var deleteSubGolonganObatFragment: DeleteSubGolonganObatFragment
    private lateinit var updateSubGolonganObatFragment: UpdateSubGolonganObatFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_golongan_obat)

        SubGolonganObatPresenter(
            this,
            this
        )
        preferenceHelper = PreferenceHelper(this)

        titlebar.text = "Sub Golongan Obat"
        loading = DialogHelper.showDialogLoading(this)
        presenter.getSubGolongan(preferenceHelper?.getUser()!!.data.member[0].idMembers)

        add_sub_golongan_obat.setOnClickListener(this)
        back.setOnClickListener(this)
        iv_clear_sub_golongan.setOnClickListener(this)

        et_search_sub_golongan.addTextChangedListener(onSearchChange())

    }

    private fun onSearchChange(): TextWatcher? {
        return object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                searchListSubGolonganObat = ArrayList()
                if (p0?.isEmpty()!!) {
                    searchListSubGolonganObat = listSubGolonganObat
                    iv_clear_sub_golongan.visibility = View.GONE
                } else {
                    iv_clear_sub_golongan.visibility = View.VISIBLE
                    searchListSubGolonganObat = filterSubGolonganObat(p0.toString())
                }

                val clickAction = object : SubGolonganObatAdapter.OnClickAction {

                    override fun onCLickUpdate(position: SubGolonganObatResponse.Data?) {
                        updateSubGolonganObatFragment = UpdateSubGolonganObatFragment()
                        val bundle = Bundle()
                        bundle.putString(
                            UpdateSubGolonganObatFragment.ID_SUB_GOLONGAN_OBAT,
                            position?.idSubGolongan
                        )
                        bundle.putString(
                            UpdateSubGolonganObatFragment.NAMA_SUB_GOLONGAN_OBAT,
                            position?.namaSubGolongan
                        )
                        updateSubGolonganObatFragment.arguments = bundle
                        updateSubGolonganObatFragment.setStyle(
                            DialogFragment.STYLE_NORMAL,
                            R.style.DialogFragmentTheme
                        )
                        updateSubGolonganObatFragment.show(
                            supportFragmentManager.beginTransaction(),
                            "Update Sub Golongan Obat"
                        )
                    }

                    override fun onClickDelete(position: SubGolonganObatResponse.Data?) {
                        deleteSubGolonganObatFragment = DeleteSubGolonganObatFragment()
                        val bundle = Bundle()
                        bundle.putString(
                            UpdateSubGolonganObatFragment.ID_SUB_GOLONGAN_OBAT,
                            position?.idSubGolongan
                        )
                        bundle.putString(
                            UpdateSubGolonganObatFragment.NAMA_SUB_GOLONGAN_OBAT,
                            position?.namaSubGolongan
                        )
                        deleteSubGolonganObatFragment.arguments = bundle
                        deleteSubGolonganObatFragment.setStyle(
                            DialogFragment.STYLE_NORMAL,
                            R.style.DialogFragmentTheme
                        )
                        deleteSubGolonganObatFragment.show(
                            supportFragmentManager.beginTransaction(),
                            "Update Sub Golongan Obat"
                        )
                    }

                }

                subGolonganObatAdapter =
                    SubGolonganObatAdapter(searchListSubGolonganObat, baseContext, clickAction)
                rv_master_data_sub_golongan_obat.adapter = subGolonganObatAdapter
                rv_master_data_sub_golongan_obat.layoutManager =
                    LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL, false)

            }

        }
    }

    private fun filterSubGolonganObat(search: String): ArrayList<SubGolonganObatResponse.Data>? {
        val result: ArrayList<SubGolonganObatResponse.Data> = ArrayList()
        for (i in listSubGolonganObat?.indices!!) {
            if (listSubGolonganObat!!.get(i).namaSubGolongan.toLowerCase(Locale.ROOT).contains(
                    search.toLowerCase(
                        Locale.ROOT
                    )
                )
            ) {
                result.add(listSubGolonganObat!![i])
            }
        }
        return result
    }

    override fun onSubGolonganError(code: Int, message: String) {
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

    override fun onSubGolonganSuccess(it: ArrayList<SubGolonganObatResponse.Data>) {
        this.listSubGolonganObat = it

        val clickAction = object : SubGolonganObatAdapter.OnClickAction {

            override fun onCLickUpdate(position: SubGolonganObatResponse.Data?) {
                updateSubGolonganObatFragment = UpdateSubGolonganObatFragment()
                val bundle = Bundle()
                bundle.putString(
                    UpdateSubGolonganObatFragment.ID_SUB_GOLONGAN_OBAT,
                    position?.idSubGolongan
                )
                bundle.putString(
                    UpdateSubGolonganObatFragment.NAMA_SUB_GOLONGAN_OBAT,
                    position?.namaSubGolongan
                )
                updateSubGolonganObatFragment.arguments = bundle
                updateSubGolonganObatFragment.setStyle(
                    DialogFragment.STYLE_NORMAL,
                    R.style.DialogFragmentTheme
                )
                updateSubGolonganObatFragment.show(
                    supportFragmentManager.beginTransaction(),
                    "Update Sub Golongan Obat"
                )
            }

            override fun onClickDelete(position: SubGolonganObatResponse.Data?) {
                deleteSubGolonganObatFragment = DeleteSubGolonganObatFragment()
                val bundle = Bundle()
                bundle.putString(
                    UpdateSubGolonganObatFragment.ID_SUB_GOLONGAN_OBAT,
                    position?.idSubGolongan
                )
                bundle.putString(
                    UpdateSubGolonganObatFragment.NAMA_SUB_GOLONGAN_OBAT,
                    position?.namaSubGolongan
                )
                deleteSubGolonganObatFragment.arguments = bundle
                deleteSubGolonganObatFragment.setStyle(
                    DialogFragment.STYLE_NORMAL,
                    R.style.DialogFragmentTheme
                )
                deleteSubGolonganObatFragment.show(
                    supportFragmentManager.beginTransaction(),
                    "Update Sub Golongan Obat"
                )
            }

        }
        subGolonganObatAdapter = SubGolonganObatAdapter(it, this, clickAction)
        rv_master_data_sub_golongan_obat.adapter = subGolonganObatAdapter
        rv_master_data_sub_golongan_obat.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
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
            add_sub_golongan_obat.id -> {
                createSubGolonganObatFragment = CreateSubGolonganObatFragment()
                createSubGolonganObatFragment.setStyle(
                    DialogFragment.STYLE_NORMAL,
                    R.style.DialogFragmentTheme
                )
                createSubGolonganObatFragment.show(
                    supportFragmentManager.beginTransaction(),
                    "Create Sub Golongan Obat"
                )
            }
            back.id -> finish()
            iv_clear_sub_golongan.id -> et_search_sub_golongan.setText("")
        }
    }

    override fun onCloseFragmentCreateSubGolonganObat(b: Boolean) {
        presenter.getSubGolongan(preferenceHelper?.getUser()!!.data.member[0].idMembers)
        et_search_sub_golongan.setText("")
    }

    override fun onCloseFragmentUpdateSubGolonganObat(b: Boolean) {
        presenter.getSubGolongan(preferenceHelper?.getUser()!!.data.member[0].idMembers)
        et_search_sub_golongan.setText("")
    }

    override fun onCloseFragmentDeleteSubGolonganObat(b: Boolean) {
        presenter.getSubGolongan(preferenceHelper?.getUser()!!.data.member[0].idMembers)
        et_search_sub_golongan.setText("")
    }

}