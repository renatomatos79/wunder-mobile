package org.wunder.helpers

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.widget.TextView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.ui.IconGenerator
import org.wunder.R
import org.wunder.data.PlaceMarkData
import org.wunder.data.PlaceMarksData

object MapsHelper {

    fun createBitmap(context: Context, mark: PlaceMarkData): Bitmap? {
        try {
            val text = TextView(context)
            text.setText(mark.name)
            val generator = IconGenerator(context)
            generator.setBackground(context.getDrawable(R.drawable.custom_info_bubble))
            generator.setContentView(text)
            val icon = generator.makeIcon()
            return icon;
        } catch (ex: Exception){
            LogHelper.addException(ex, "MapsHelper");
        }
        return null;
    }

    fun createOptions(context: Context, mark: PlaceMarkData, icon: Bitmap?): MarkerOptions? {
        try {
            val markOption = MarkerOptions()
                    .position(mark.latlong()!!)
                    .alpha(0.7f)
                    .snippet(mark.name + ": " + mark.fuel)
                    .icon(BitmapDescriptorFactory.fromBitmap(icon))
            return markOption;
        } catch (ex: Exception){
            LogHelper.addException(ex, "MapsHelper");
        }
        return null;
    }

    fun createMap(map: GoogleMap, mark: PlaceMarkData, markerOptions: MarkerOptions){
        try
        {
            val mk = map.addMarker(markerOptions)
            mk.tag = mark
        }
        catch (ex: Exception){
            LogHelper.addException(ex, "MapsHelper")
        }
    }

    fun gotoLocation(map: GoogleMap, latLng: LatLng, zoom: Float){
        try
        {
            val cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, zoom)
            map.moveCamera(cameraUpdate)
            map.animateCamera(cameraUpdate);
        }
        catch (ex: Exception){
            LogHelper.addException(ex, "MapsHelper")
        }
    }

}