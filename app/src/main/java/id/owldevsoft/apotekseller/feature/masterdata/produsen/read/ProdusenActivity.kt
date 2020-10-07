package id.owldevsoft.apotekseller.feature.masterdata.produsen.read

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
import id.owldevsoft.apotekseller.adapter.ProdusenAdapter
import id.owldevsoft.apotekseller.feature.masterdata.produsen.create.CreateProdusenFragment
import id.owldevsoft.apotekseller.feature.masterdata.produsen.delete.DeleteProdusenFragment
import id.owldevsoft.apotekseller.feature.masterdata.produsen.update.UpdateProdusenFragment
import id.owldevsoft.apotekseller.model.ProdusenResponse
import id.owldevsoft.apotekseller.utils.DialogHelper
import id.owldevsoft.apotekseller.utils.PreferenceHelper
import kotlinx.android.synthetic.main.action_bar.*
import kotlinx.android.synthetic.main.activity_produsen.*
import java.util.*
import kotlin.collections.ArrayList

class ProdusenActivity : AppCompatActivity(),
    ProdusenContract.View, View.OnClickListener,
    CreateProdusenFragment.OnCloseFragmentCreateProdusen,
    UpdateProdusenFragment.OnCLoseFragmentUpdateProdusen,
    DeleteProdusenFragment.OnCloseFragmentDeleteProdusen {

    override lateinit var presenter: ProdusenContract.Presenter
    private var preferenceHelper: PreferenceHelper? = null

    private var loading: AlertDialog? = null
    private var produsenAdapter: ProdusenAdapter? = null
    private var listProdusen: ArrayList<ProdusenResponse.Data>? = null
    private var searchListProdusen: ArrayList<ProdusenResponse.Data>? = null

    private lateinit var createProdusenFragment: CreateProdusenFragment
    private lateinit var updateProdusenFragment: UpdateProdusenFragment
    private lateinit var deleteProdusenFragment: DeleteProdusenFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_produsen)

        ProdusenPresenter(
            this,
            this
        )
        preferenceHelper = PreferenceHelper(this)

        titlebar.text = "Produsen"
        loading = DialogHelper.showDialogLoading(this)
        presenter.getProdusen(preferenceHelper?.getUser()!!.data.member[0].idMembers)

        add_produsen_obat.setOnClickListener(this)
        back.setOnClickListener(this)
        iv_clear_produsen.setOnClickListener(this)

        et_search_produsen.addTextChangedListener(onSearchChange())

    }

    private fun onSearchChange(): TextWatcher? {
        return object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                searchListProdusen = ArrayList()
                if (p0?.isEmpty()!!) {
                    searchListProdusen = listProdusen
                    iv_clear_produsen.visibility = View.GONE
                } else {
                    iv_clear_produsen.visibility = View.VISIBLE
                    searchListProdusen = filterProdusen(p0.toString())
                }

                val clickAction = object : ProdusenAdapter.OnClickActionProdusen {
                    override fun onCLickUpdate(position: ProdusenResponse.Data?) {
                        updateProdusenFragment = UpdateProdusenFragment()
                        val bundle = Bundle()
                        bundle.putString(UpdateProdusenFragment.ID_MERK, position?.idMerk)
                        bundle.putString(UpdateProdusenFragment.NAMA_MERK, position?.namaMerk)
                        updateProdusenFragment.arguments = bundle
                        updateProdusenFragment.setStyle(
                            DialogFragment.STYLE_NORMAL,
                            R.style.DialogFragmentTheme
                        )
                        updateProdusenFragment.show(
                            supportFragmentManager.beginTransaction(),
                            "Delete Produsen"
                        )
                    }

                    override fun onClickDelete(position: ProdusenResponse.Data?) {
                        deleteProdusenFragment = DeleteProdusenFragment()
                        val bundle = Bundle()
                        bundle.putString(DeleteProdusenFragment.ID_MERK, position?.idMerk)
                        bundle.putString(DeleteProdusenFragment.NAMA_MERK, position?.namaMerk)
                        deleteProdusenFragment.arguments = bundle
                        deleteProdusenFragment.setStyle(
                            DialogFragment.STYLE_NORMAL,
                            R.style.DialogFragmentTheme
                        )
                        deleteProdusenFragment.show(
                            supportFragmentManager.beginTransaction(),
                            "Delete Produsen"
                        )
                    }

                }

                produsenAdapter = ProdusenAdapter(searchListProdusen, baseContext, clickAction)
                rv_master_data_produsen.adapter = produsenAdapter
                rv_master_data_produsen.layoutManager =
                    LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL, false)

            }

        }
    }

    override fun onMerkError(code: Int, message: String) {
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

    override fun onMerkSuccess(it: ArrayList<ProdusenResponse.Data>) {
        this.listProdusen = it

        val clickAction = object : ProdusenAdapter.OnClickActionProdusen {

            override fun onCLickUpdate(position: ProdusenResponse.Data?) {
                updateProdusenFragment = UpdateProdusenFragment()
                val bundle = Bundle()
                bundle.putString(UpdateProdusenFragment.ID_MERK, position?.idMerk)
                bundle.putString(UpdateProdusenFragment.NAMA_MERK, position?.namaMerk)
                updateProdusenFragment.arguments = bundle
                updateProdusenFragment.setStyle(
                    DialogFragment.STYLE_NORMAL,
                    R.style.DialogFragmentTheme
                )
                updateProdusenFragment.show(
                    supportFragmentManager.beginTransaction(),
                    "Update Produsen"
                )
            }

            override fun onClickDelete(position: ProdusenResponse.Data?) {
                deleteProdusenFragment = DeleteProdusenFragment()
                val bundle = Bundle()
                bundle.putString(DeleteProdusenFragment.ID_MERK, position?.idMerk)
                bundle.putString(DeleteProdusenFragment.NAMA_MERK, position?.namaMerk)
                deleteProdusenFragment.arguments = bundle
                deleteProdusenFragment.setStyle(
                    DialogFragment.STYLE_NORMAL,
                    R.style.DialogFragmentTheme
                )
                deleteProdusenFragment.show(
                    supportFragmentManager.beginTransaction(),
                    "Delete Produsen"
                )
            }

        }
        produsenAdapter = ProdusenAdapter(it, this, clickAction)
        rv_master_data_produsen.adapter = produsenAdapter
        rv_master_data_produsen.layoutManager =
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
            add_produsen_obat.id -> {
                createProdusenFragment = CreateProdusenFragment()
                createProdusenFragment.setStyle(
                    DialogFragment.STYLE_NORMAL,
                    R.style.DialogFragmentTheme
                )
                createProdusenFragment.show(
                    supportFragmentManager.beginTransaction(),
                    "Create Produsen"
                )
            }
            back.id -> finish()
            iv_clear_produsen.id -> et_search_produsen.setText("")
        }
    }

    private fun filterProdusen(search: String): ArrayList<ProdusenResponse.Data>? {
        val result: ArrayList<ProdusenResponse.Data> = ArrayList()
        for (i in listProdusen?.indices!!) {
            if (listProdusen!!.get(i).namaMerk.toLowerCase(Locale.ROOT).contains(
                    search.toLowerCase(
                        Locale.ROOT
                    )
                )
            ) {
                result.add(listProdusen!![i])
            }
        }
        return result
    }

    override fun onCloseFragmentCreateProdusen(b: Boolean) {
        presenter.getProdusen(preferenceHelper?.getUser()!!.data.member[0].idMembers)
        et_search_produsen.setText("")
    }

    override fun onCloseFragmentUpdateProdusen(b: Boolean) {
        presenter.getProdusen(preferenceHelper?.getUser()!!.data.member[0].idMembers)
        et_search_produsen.setText("")
    }

    override fun onCloseFragmentDeleteProdusen(b: Boolean) {
        presenter.getProdusen(preferenceHelper?.getUser()!!.data.member[0].idMembers)
        et_search_produsen.setText("")
    }

}