package org.wunder.helpers

import android.app.Activity
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

    fun showMessage(activity: Activity, message: String){
        val title = activity.resources.getString(R.string.dialog_atention)
        DialogHelper.showMessage(activity, title, message)
    }

    fun showMessage(activity: Activity, resourceID: Int){
        DialogHelper.showMessage(activity, R.string.dialog_atention, resourceID)
    }

    fun showWarning(activity: Activity){
        val message = activity.resources.getString(R.string.dialog_warning)
        AppHelper.showMessage(activity, message)
    }


}