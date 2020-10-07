package id.owldevsoft.apotekseller.feature.login

import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import id.owldevsoft.apotekseller.R
import id.owldevsoft.apotekseller.feature.main.MainActivity
import id.owldevsoft.apotekseller.feature.register.RegisterActivity
import id.owldevsoft.apotekseller.model.LoginBody
import id.owldevsoft.apotekseller.model.LoginResponse
import id.owldevsoft.apotekseller.utils.*
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), LoginContract.View, View.OnClickListener {

    override lateinit var presenter: LoginContract.Presenter
    private lateinit var permission: PermissionCallback.Presenter
    private lateinit var loginBody: LoginBody
    private var preferenceHelper: PreferenceHelper? = null

    init {
        LoginPresenter(this, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

        preferenceHelper = PreferenceHelper(this)

        btnLogin.setOnClickListener(this)
        tvForgot.setOnClickListener(this)
        tvSingup.setOnClickListener(this)

        permission = PermissionHelper(this)
        permission.isRequestPermissionGranted(this)

    }

//    override fun onResume() {
//        super.onResume()
//
//        if (preferenceHelper?.dataUser != null && !preferenceHelper!!.dataUser.equals("")) {
//            val intent = Intent(applicationContext, MainActivity::class.java)
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//            startActivity(intent)
//            finish()
//        }
//
//    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            btnLogin.id -> {
                if (handleInput()) {
                    loginBody = LoginBody(etUsername.text.toString(), etPass.text.toString())
                    presenter.doLogin(loginBody)
                }
            }
            tvForgot.id -> {

            }
            tvSingup.id -> {
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onLoginError(code: Int, message: String) {
        DialogHelper.showWarnDialog(
            this,
            getString(R.string.err_mes_login, message),
            getString(R.string.ans_mes_ok),
            false,
            object : DialogHelper.Positive {
                override fun positiveButton(dialog: DialogInterface, id: Int) {
                    dialog.dismiss()
                }
            })
    }

    override fun onLoginSuccess(loginResponse: LoginResponse) {
        preferenceHelper?.dataUser = Gson().toJson(loginResponse)
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }

    override fun onProcess(boolean: Boolean) {
        if (boolean) {
            loadingProg.visibility = View.VISIBLE
            btnLogin.visibility = View.GONE
        } else {
            loadingProg.visibility = View.GONE
            btnLogin.visibility = View.VISIBLE
        }
    }

    private fun handleInput(): Boolean {
        if (etUsername.text.isEmpty()) {
            etUsername.error = getString(R.string.fill_data)
            etUsername.requestFocus()
            return false
        }
        if (etPass.text.isEmpty()) {
            etPass.error = getString(R.string.fill_data)
            etPass.requestFocus()
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val grantResult = IntArray(permissions.size)
        grantResult.forEach {
            if (requestCode == Constants.CODE_REQUEST_PERMISSION) {
                if (it == PackageManager.PERMISSION_GRANTED) {
                    Log.d("Permission", "onRequestPermissionsResult: permission granted")
//                    Toast.makeText(this, "permission granted", Toast.LENGTH_LONG).show()
                } else {
                    Log.d("Permission", "onRequestPermissionsResult: permission denied")
//                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

}