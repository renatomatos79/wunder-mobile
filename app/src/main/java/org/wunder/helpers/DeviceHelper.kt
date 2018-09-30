package org.wunder.helpers

import android.os.Build
import android.content.Context

object DeviceHelper {

    val Context.appVersion: String
        get() {
            try {
                return this.getPackageManager().getPackageInfo(getPackageName(), 0).versionName
            } catch (ex: Exception) {
                return "Não disponível"
            }

        }

    val sdkVersion: String
        get() {
            try {
                return Build.VERSION.SDK
            } catch (ex: Exception) {
                return "Não disponível"
            }

        }

    val soVersion: String
        get() {
            try {
                return Build.VERSION.RELEASE
            } catch (ex: Exception) {
                return "Não disponível"
            }

        }

    val model: String
        get() {
            try {
                return android.os.Build.MODEL
            } catch (ex: Exception) {
                return "Não disponível"
            }

        }

}
