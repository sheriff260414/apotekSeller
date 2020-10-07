package id.owldevsoft.apotekseller.feature.splash

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import id.owldevsoft.apotekseller.R
import id.owldevsoft.apotekseller.feature.home.HomeContract
import id.owldevsoft.apotekseller.feature.home.HomePresenter
import id.owldevsoft.apotekseller.feature.login.LoginActivity
import id.owldevsoft.apotekseller.feature.main.MainActivity
import id.owldevsoft.apotekseller.utils.DialogHelper
import id.owldevsoft.apotekseller.utils.LogoutCallback
import id.owldevsoft.apotekseller.utils.LogoutHandle
import id.owldevsoft.apotekseller.utils.PreferenceHelper

class SplashActivity : AppCompatActivity(), SplashContract.View {

    override lateinit var presenter: SplashContract.Presenter
    private var preferenceHelper: PreferenceHelper? = null
    private lateinit var logoutCallback: LogoutCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        SplashPresenter(this, this)
        preferenceHelper = PreferenceHelper(this)
        logoutCallback = LogoutHandle(this)

        presenter.getBanner()

    }

    override fun onError(code: Int, message: String) {
        if (code == 401) {
            Handler().postDelayed({
                logoutCallback.isLogout(this)
            }, 2000)
        } else {
            DialogHelper.showWarnDialog(
                this,
                message,
                "Reload",
                false,
                object : DialogHelper.Positive {
                    override fun positiveButton(dialog: DialogInterface, id: Int) {
                        presenter.getBanner()
                        dialog.dismiss()
                    }
                })
        }
    }

    override fun onBannerSuccess(message: String) {
        Handler().postDelayed({
            if (preferenceHelper?.getUser() != null && !preferenceHelper!!.getUser()?.equals("")!!) {
                val intent = Intent(applicationContext, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(applicationContext, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
                finish()
            }
        }, 1000)
    }

    override fun onProcess(boolean: Boolean) {

    }
}