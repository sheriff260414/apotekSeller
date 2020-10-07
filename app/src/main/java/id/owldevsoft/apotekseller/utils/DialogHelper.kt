package id.owldevsoft.apotekseller.utils

import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import id.owldevsoft.apotekseller.R

object DialogHelper {

    fun showWarnDialog(
        context: Context,
        message: String,
        positiveMsg: String,
        isCancelable: Boolean,
        answer: Positive
    ) {

        val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(context)

        alertDialogBuilder.setMessage(message)
            .setCancelable(isCancelable)
            .setPositiveButton(positiveMsg, answer::positiveButton)

        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.setCancelable(isCancelable)
        alertDialog.show()
    }

    fun showConfirmDialog(
        context: Context,
        message: String,
        positiveMsg: String,
        negativeMsg: String,
        isCancelable: Boolean,
        answer: Answer
    ) {
        val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(context)

        alertDialogBuilder.setMessage(message)
            .setCancelable(isCancelable)
            .setPositiveButton(positiveMsg, answer::positiveButton)
            .setNegativeButton(negativeMsg, answer::negativeButton)

        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.setCancelable(isCancelable)
        alertDialog.show()
    }

    interface Answer {
        fun positiveButton(dialog: DialogInterface, id: Int)
        fun negativeButton(dialog: DialogInterface, id: Int)
    }

    interface Positive {
        fun positiveButton(dialog: DialogInterface, id: Int)
    }

    fun showDialogLoading(context: Context): AlertDialog {
        val view = LayoutInflater.from(context).inflate(R.layout.item_layout, null, false)
        val dialog = AlertDialog.Builder(context)
            .setView(view)
            .setCancelable(false)
            .create()
        return dialog
    }

    fun showDialogCustomView(context: Context, view: View, hideButton: Boolean, cancelable: Boolean, btnName: String? = null, listener: DialogInterface.OnClickListener? = null) : AlertDialog {
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setView(view)
        if (listener != null) {
            dialogBuilder.setPositiveButton(btnName, listener)
        }
        dialogBuilder.setCancelable(cancelable)
        if (!hideButton) {
            dialogBuilder.setNegativeButton("batal") { dialog, _ ->
                dialog?.dismiss()
            }
        }

        return dialogBuilder.create()
    }

}