package id.owldevsoft.apotekseller.utils

import android.app.Activity

interface PermissionCallback {

    interface Presenter {
        fun isRequestPermissionGranted(context: Activity) : Boolean
    }

}