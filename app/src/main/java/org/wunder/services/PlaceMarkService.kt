package org.wunder.services

import com.github.kittinunf.fuel.httpGet
import org.wunder.data.PlaceMarkData
import org.wunder.data.PlaceMarksData
import org.wunder.helpers.ConstantsHelper
import org.wunder.helpers.JsonHelper
import org.wunder.helpers.JsonHelper.getFromJSON
import org.wunder.helpers.LogHelper
import org.wunder.interfaces.OnDownloadListener

object PlaceMarksService {

    var placeMarks: PlaceMarksData? = null

    private fun downloadJSON(onDownloadListener: OnDownloadListener<String>) {
        ConstantsHelper.PLACE_MARKS_URL.httpGet().responseString { request, response, result ->
            when (result) {
                is com.github.kittinunf.result.Result.Failure -> {
                    LogHelper.addException(result.getException(), "PlaceMarkService")
                    onDownloadListener.error(result.getException())
                }
                is com.github.kittinunf.result.Result.Success -> {
                    onDownloadListener.complete(result.get())
                }
            }
        }

    }

    fun marks(placeMarksOnDownloadListener: OnDownloadListener<PlaceMarksData>) {
        try {
            downloadJSON(object : OnDownloadListener<String> {
                override fun error(ex: Exception) {
                    placeMarksOnDownloadListener.error(ex)
                }

                override fun complete(json: String) {
                    val r = getFromJSON(json, Map::class.java)
                    val innerJson = JsonHelper.toJSON(r["placemarks"])
                    var marks = getFromJSON(innerJson, Array<PlaceMarkData>::class.java).toList()
                    placeMarks = PlaceMarksData(marks)
                    placeMarksOnDownloadListener.complete(placeMarks!!)
                }
            })
        } catch (ex: Exception) {
            LogHelper.addException(ex, "PlaceMarkService");
            placeMarksOnDownloadListener.error(ex)
        }

    }

}