package org.wunder.helpers

import android.Manifest

import java.util.ArrayList

object ConstantsHelper {
    var PLACE_MARKS_URL = "https://s3-us-west-2.amazonaws.com/wunderbucket/locations.json";

    var APP_NAME = "LuqyTV"
    var STORAGE_DEFAULT = "Storage_Default"
    var STORAGE_DEFAULT_INTERNAL = "Storage_Default_Internal"
    var STORAGE_DEFAULT_SDCARD = "Storage_Default_SDCard"

    var DEVICE_ID = "DeviceID"
    var DEVICE_SERVER_ID = "DeviceServerID"
    var DEVICE_NAME = "DeviceName"
    var DEVICE_PLACE_NAME = "DevicePlaceName"
    var DEVICE_LOCAL_REGISTRATION_DATE = "DeviceLocalRegistrationDate"
    var DEVICE_STORE_ID = "DeviceStoreID"

    var DISPLAY_IMAGE_SCALE_TYPE = "DisplayImageScaleType"

    var UPDATE_DISPLAYS_INTERVAL = "UpdateDisplaysInterval"
    var UPDATE_WEATHER_INTERVAL = "UpdateWeatherInterval"
    var DEBUG_ENABLED = "DebugEnabled"

    var UPDATE_DISPLAYS_INTERVAL_DEFAULT_MINUTE_VALUE = 30
    var UPDATE_WEATHER_INTERVAL_DEFAULT_MINUTE_VALUE = 10

    var BUNDLE_DISPLAY_ITEM = "BundleDisplayItem"
    var BUNDLE_PRICE = "BundlePrice"
    var BUNDLE_IMAGE = "BundleImage"
    var BUNDLE_BTTOMBAR_IMAGE = "BundleBottomBarImage"
    var BUNDLE_VIDEO = "BundleVideo"
    var BUNDLE_ONE_LINE = "BundleOneLine"
    var BUNDLE_TWO_LINES = "BundleTwoLines"
    var BUNDLE_THREE_LINES = "BundleThreeLines"
    var BUNDLE_SCHEDULE = "BundleSchedule"
    var BUNDLE_LAST_FRAGMENT = "BundleLastFragment"

    var FIREBASE_COLLECTION_DEVICES = "devices"
    var FIREBASE_COLLECTION_WEATHER = "weather"
    var FIREBASE_COLLECTION_DISPLAYS = "displays"
    var FIREBASE_COLLECTION_STORE = "stores"

    var REQUEST_CODE_PERMISSIONS = 1

    val ONE_SECOND = 1000L
    val THREE_SECONDS: Long? = 3 * ONE_SECOND
    val SIX_SECONDS: Long? = 6 * ONE_SECOND
    val ONE_MINUTE: Long? = 60 * ONE_SECOND
    val FIVE_MINUTES: Long? = 1 * ONE_MINUTE!!
    val TEN_MINUTES: Long? = 10 * ONE_MINUTE!!
    val ONE_HOUR: Long? = 60 * ONE_MINUTE!!
    val ONE_DAY: Long? = 24 * ONE_HOUR!!

    val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.INTERNET)

}