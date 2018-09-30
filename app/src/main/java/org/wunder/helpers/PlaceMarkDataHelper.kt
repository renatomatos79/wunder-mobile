package org.wunder.helpers

import com.google.android.gms.maps.model.LatLng
import org.wunder.R
import org.wunder.data.PlaceMarkData

public object PlaceMarkDataHelper {

    fun latlong(mark: PlaceMarkData): LatLng? {
        try
        {
            // avoid array out of bounds
            if (mark.coordinates.size != 3){
                return null;
            }
            // found out latlng
            val lat = mark.coordinates[1]
            val lng = mark.coordinates[0]
            return LatLng(lat!!, lng!!)
        }
        catch (ex: Exception){
            LogHelper.addException(ex, "PlaceMarkData")
        }
        return null
    }

    fun interiorEvaluation(mark: PlaceMarkData): Int
    {
        return convertEvaluation(mark.interior)
    }

    fun exteriorEvaluation(mark: PlaceMarkData): Int
    {
        return convertEvaluation(mark.exterior)
    }

    fun convertEvaluation(value: String): Int
    {
        if (value == "GOOD"){
            return R.drawable.ic_thumb_up
        } else {
            return R.drawable.ic_thumb_down
        }
    }

}