package org.wunder.helpers

import android.app.Activity
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import java.util.ArrayList

public object PermissionHelper {

    fun validate(act: Activity, requestCode: Int, vararg permissions: String) : Boolean {
        val list = ArrayList<String>()
        for (permission in permissions)
        {
            val isOK = ContextCompat.checkSelfPermission(act, permission) == PackageManager.PERMISSION_GRANTED
            if (!isOK) list.add(permission)
        }
        return list.isEmpty();
    }

    fun requestPermission(activity: Activity, permissionRequestCode: Int, permissions: Array<String>) {
        ActivityCompat.requestPermissions(activity, permissions, permissionRequestCode)
    }
}
