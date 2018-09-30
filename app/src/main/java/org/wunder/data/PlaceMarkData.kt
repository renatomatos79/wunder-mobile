package org.wunder.data

import android.graphics.drawable.Icon
import com.google.android.gms.maps.model.LatLng
import org.wunder.R
import org.wunder.helpers.LogHelper
import java.io.Serializable

data class PlaceMarkData (var address: String, var coordinates: Array<Double>, var engineType: String, var exterior: String, var fuel: Int, var interior: String, var name: String, var vin: String) : Serializable
