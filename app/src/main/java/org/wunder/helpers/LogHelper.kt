package org.wunder.helpers

import android.util.Log

public object LogHelper {

    fun addMessage(msg: String, logCat: Int, logCatTag: String?){
        if (logCat.equals(Log.ERROR)){
            Log.e(logCatTag, msg);
        } else {
            Log.w(logCatTag, msg);
        }
    }

    fun addException(ex: Exception, logCatTag: String?){
        addMessage(ex.toString(), Log.ERROR, logCatTag);
    }

    fun addWarn(ex: Exception, logCatTag: String?){
        addMessage(ex.toString(), Log.WARN, logCatTag);
    }
}