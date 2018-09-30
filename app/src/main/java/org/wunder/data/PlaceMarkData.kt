package org.wunder.data

import com.google.android.gms.maps.model.LatLng
import org.wunder.helpers.LogHelper

data class PlaceMarkData (var address: String, var coordinates: Array<Double>, var engineType: String, var exterior: String, var fuel: Int, var interior: String, var name: String, var vin: String)
{
    private var _latlng: LatLng? = null;

    fun latlong(): LatLng? {
        try
        {
            // check if the latlng has been already found out
            if (this._latlng != null){
                return _latlng;
            }
            // avoid array out of bounds
            if (this.coordinates.size != 3){
                return null;
            }
            // found out latlng
            val lat = coordinates[0]
            val lng = coordinates[1]
            _latlng = LatLng(lat!!, lng!!)
        }
        catch (ex: Exception){
            LogHelper.addException(ex, "PlaceMarkData")
        }
        return _latlng;
    }

}