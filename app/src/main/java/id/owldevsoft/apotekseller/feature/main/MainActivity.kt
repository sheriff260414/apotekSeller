package id.owldevsoft.apotekseller.feature.main

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import id.owldevsoft.apotekseller.R
import id.owldevsoft.apotekseller.feature.home.HomeFragment
import id.owldevsoft.apotekseller.feature.login.LoginActivity
import id.owldevsoft.apotekseller.feature.penjualan.create.AddPenjualanFragment
import id.owldevsoft.apotekseller.feature.profil.ProfilFragment
import id.owldevsoft.apotekseller.utils.DialogHelper
import id.owldevsoft.apotekseller.utils.PreferenceHelper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener,
    BottomNavigationView.OnNavigationItemReselectedListener , ProfilFragment.OnSuccessChangePassword{

    private lateinit var homeFragment: HomeFragment
    private lateinit var profilFragment: ProfilFragment
    private lateinit var addPenjualanFragment: AddPenjualanFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        homeFragment = HomeFragment()
        addFragment(homeFragment)

        bottom_nav.setOnNavigationItemSelectedListener(this)
        bottom_nav.setOnNavigationItemReselectedListener(this)

    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_content, fragment)
            .commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_home -> {
                homeFragment = HomeFragment()
                addFragment(homeFragment)
            }
            R.id.menu_add -> {
                addPenjualanFragment =
                    AddPenjualanFragment()
                addFragment(addPenjualanFragment)
            }
            R.id.menu_user -> {
                profilFragment = ProfilFragment()
                addFragment(profilFragment)
            }
        }
        return true
    }

    override fun onNavigationItemReselected(item: MenuItem) {
        when (item.itemId) {
            R.id.menu_home -> {
            }
            R.id.menu_user -> {
            }
            R.id.menu_add -> {
            }
        }
    }

    override fun onBackPressed() {
        DialogHelper.showConfirmDialog(
            this, getString(R.string.message_keluar_app),
            getString(R.string.ans_mes_logout),
            getString(R.string.ans_mes_no_logout),
            false,
            object : DialogHelper.Answer {
                override fun positiveButton(dialog: DialogInterface, id: Int) {
                    val a = Intent(Intent.ACTION_MAIN)
                    a.addCategory(Intent.CATEGORY_HOME)
                    a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(a)
                }

                override fun negativeButton(dialog: DialogInterface, id: Int) {
                    dialog.dismiss()
                }

            }
        )

//        val builder =
//            AlertDialog.Builder(this@MainActivity)
//        builder.setMessage("Apakah anda yakin keluar dari aplikasi ini?")
//            .setPositiveButton(
//                "YA"
//            ) { dialogInterface: DialogInterface?, i: Int ->
//                val a = Intent(Intent.ACTION_MAIN)
//                a.addCategory(Intent.CATEGORY_HOME)
//                a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//                startActivity(a)
//            }
//            .setNegativeButton("TIDAK", null)
//            .setCancelable(false)
//        val alertDialog = builder.create()
//        alertDialog.show()
    }

    override fun onSuccessChangePassword() {
        homeFragment = HomeFragment()
        addFragment(homeFragment)
        bottom_nav.menu.findItem(R.id.menu_home).isChecked = true
    }

}