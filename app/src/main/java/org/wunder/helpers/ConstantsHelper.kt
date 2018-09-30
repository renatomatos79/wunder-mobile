package org.wunder.helpers

import android.Manifest

object ConstantsHelper {
    var PLACE_MARKS_URL = "https://s3-us-west-2.amazonaws.com/wunderbucket/locations.json";
    var MAPS_DEFAULT_ZOOM = 15.0f
    var REQUEST_CODE_PERMISSIONS = 1
    val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.INTERNET, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
}