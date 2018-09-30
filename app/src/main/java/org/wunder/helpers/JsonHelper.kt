package org.wunder.helpers

import com.google.gson.GsonBuilder

object JsonHelper {

    private val gson = GsonBuilder().create()

    fun <T> getFromJSON(json: String, clazz: Class<T>): T {
        return gson.fromJson(json, clazz)
    }

    fun <T> toJSON(clazz: T): String {
        return gson.toJson(clazz)
    }
}