package org.wunder.helpers

import android.content.Context
import android.content.Intent

public object ActivityHelper {

    fun Context.launchActitity(activity: Class<*>){
        val myIntent = Intent(this, activity)
        this.startActivity(myIntent)
    }

}