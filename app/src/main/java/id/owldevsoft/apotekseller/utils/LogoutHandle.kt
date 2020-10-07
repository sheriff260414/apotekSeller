package id.owldevsoft.apotekseller.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import id.owldevsoft.apotekseller.feature.login.LoginActivity
import id.owldevsoft.apotekseller.feature.main.MainActivity

class LogoutHandle(val context: Context) : LogoutCallback {

    private val preferenceHelper = PreferenceHelper(context)

    override fun isLogout(context: Activity) {
        preferenceHelper.remove(PreferenceHelper.USERDATA)
        preferenceHelper.dataUser = null
        val intent = Intent(context, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        context.startActivity(intent)
        context.finish()
    }

}