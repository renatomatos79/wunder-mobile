package org.wunder.services

import com.github.kittinunf.fuel.httpGet
import org.wunder.data.PlaceMarkData

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.wunder.data.PlaceMarksData
import org.wunder.helpers.ConstantsHelper
import org.wunder.helpers.JsonHelper
import org.wunder.helpers.JsonHelper.getFromJSON
import org.wunder.helpers.LogHelper
import org.wunder.interfaces.DownloadListener

public object CarService {

    private fun downloadJSON(downloadListener: DownloadListener<String>) {
        ConstantsHelper.PLACE_MARKS_URL.httpGet().responseString { request, response, result ->
            when (result) {
                is com.github.kittinunf.result.Result.Failure -> {
                    LogHelper.addException(result.getException(), "PlaceMarkService")
                    downloadListener.error(result.getException())
                }
                is com.github.kittinunf.result.Result.Success -> {
                    downloadListener.complete(result.get())
                }
            }
        }

    }

    fun marks(placeMarksDownloadListener: DownloadListener<PlaceMarksData>) {
        try {
            downloadJSON(object : DownloadListener<String> {
                override fun error(ex: Exception) {
                    placeMarksDownloadListener.error(ex)
                }

                override fun complete(json: String) {
                    val r = getFromJSON(json, Map::class.java)
                    val innerJson = JsonHelper.toJSON(r["placemarks"])
                    var marks = getFromJSON(innerJson, Array<PlaceMarkData>::class.java).toList()
                    var mark = PlaceMarksData(marks)
                    placeMarksDownloadListener.complete(mark)
                }
            })
        } catch (ex: Exception) {
            LogHelper.addException(ex, "PlaceMarkService");
            placeMarksDownloadListener.error(ex)
        }

    }

}