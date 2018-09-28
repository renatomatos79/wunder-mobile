package org.wunder

import org.wunder.data.CarData

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.net.URL

public object CarService {

    private fun downloadJSON(): String{
        return URL("https://s3-us-west-2.amazonaws.com/wunderbucket/locations.json").readText();
    }

    private fun carsFromJSON(json: String): List<CarData>{
        val type = object: TypeToken<List<CarData>>(){}.type
        return Gson().fromJson<List<CarData>>(json, type)
    }

    fun cars(): List<CarData>? {
        try {
            val json = downloadJSON()
            return carsFromJSON(json)
        } catch (ex: Exception) {
            ex.message
            return null
        }

    }

}