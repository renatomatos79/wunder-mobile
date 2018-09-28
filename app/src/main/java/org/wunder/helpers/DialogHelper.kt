package org.wunder.helpers

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AlertDialog


public object DialogHelper {

    fun showProgress(context: Context, title: String, message: String): ProgressDialog {
        val dialog = ProgressDialog.show(context, title, message, false, true);
        return dialog;
    }

    fun close(dialog: Dialog){
        dialog.dismiss();
    }

    fun showMessage(context: Context, title: String, message: String) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setCancelable(true)
        builder.setNeutralButton(android.R.string.ok, DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })
        val dialog = builder.create()
        dialog.show()
    }

    fun showMessage(context: Context, titleResourceID: Int, messageResourceID: Int){
        val title = context.resources.getString(titleResourceID)
        val message = context.resources.getString(messageResourceID)
        showMessage(context, title, message)
    }

}