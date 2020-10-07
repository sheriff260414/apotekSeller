package id.owldevsoft.apotekseller.utils

import android.content.Context
import com.google.gson.Gson
import id.owldevsoft.apotekseller.BuildConfig
import id.owldevsoft.apotekseller.model.LoginResponse

class PreferenceHelper(val context: Context) {

    companion object {
        const val USERDATA = BuildConfig.APPLICATION_ID + ".USERDATA"
        private const val PREF_NAME = BuildConfig.APPLICATION_ID + ".PREF"
    }

    private val preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    //    SET GET DATA USER
    var dataUser = preferences?.getString(USERDATA, "")
        set(value) = preferences?.edit()?.putString(USERDATA, value)!!.apply()

//    fun setUser(loginResponse: LoginResponse) {
//        preferences?.edit()?.putString(USERDATA, Gson().toJson(loginResponse))?.apply()
//    }

    fun getUser(): LoginResponse? = Gson().fromJson(this.dataUser, LoginResponse::class.java)

    fun remove(KEY_NAME: String?) {
//        preferences?.edit()?.putString(KEY_NAME, "")?.apply()
        preferences?.edit()?.apply {
            remove(KEY_NAME)
            apply()
        }
    }

//    private val spe: SharedPreferences.Editor? = null
//    private val sp: SharedPreferences? = null
//    private val SPIDUSER = "SPIDUSER"
//    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)
//
//
//    var dataUser = preferences.getString(USERDATA, "")
//        set(value) = preferences.edit().putString(USERDATA, value).apply()
//
//
//    fun saveDataUser(response: LoginResponse?)
//    {
//        val responseLogin = Gson().toJson(response)
//        preferences.edit().putString(SPIDUSER, responseLogin).apply()
//        spe?.putString(SPIDUSER, responseLogin)
//        spe?.commit()
//    }
//
//
//    fun getDataUser(): LoginResponse?
//    {
//        val user = preferences.getString(SPIDUSER, "")
//        Log.d("Preference", user!!)
//        return Gson().fromJson(user, LoginResponse::class.java)
//    }
//
//    fun logoutUser(mContext: Context) {
//
//        spe?.clear()
//        spe?.commit()
//
//        val mIntent = Intent(mContext, LoginActivity::class.java)
//        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
//
//        context.startActivity(mIntent)
//    }

}