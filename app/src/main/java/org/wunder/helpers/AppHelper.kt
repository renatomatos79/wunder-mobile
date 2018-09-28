package org.wunder.helpers

import android.app.Activity
import android.content.Context
import android.text.TextUtils
import android.widget.ImageView
import org.wunder.R

public object AppHelper {

    fun hasPermisions(activity: Activity) : Boolean {
        return PermissionHelper.validate(activity, ConstantsHelper.REQUEST_CODE_PERMISSIONS, *ConstantsHelper.REQUIRED_PERMISSIONS);
    }

    fun valiatePermissions(activity: Activity) : Boolean {
        val isOk = hasPermisions(activity)
        if (!isOk){
            showMessage(activity, R.string.dialog_permissions_must_be_allowed)
            return false
        }
        return true
    }

    fun showMessage(context: Context, message: String){
        val title = context.resources.getString(R.string.dialog_atention)
        DialogHelper.showMessage(context, title, message)
    }

    fun showMessage(context: Context, resourceID: Int){
        DialogHelper.showMessage(context, R.string.dialog_atention, resourceID)
    }


}