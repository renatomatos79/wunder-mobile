package org.wunder

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.View
import android.widget.Button
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.wunder.data.PlaceMarkData
import android.widget.EditText
import android.widget.LinearLayout
import android.content.pm.PackageManager
import org.wunder.helpers.AppHelper
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import android.widget.TextView
import com.google.maps.android.ui.IconGenerator
import org.wunder.helpers.LogHelper
import org.wunder.helpers.MapsHelper
import org.wunder.services.PlaceMarksService

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private var lastLocation: LatLng? = null
    private var repositionMap = true
    private var mapFragment: SupportMapFragment? = null
    private var llySearch: LinearLayout? = null
    private var txtAddress: EditText? = null
    private var btnFilter: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        llySearch = findViewById(R.id.llySearch) as LinearLayout
        txtAddress = findViewById(R.id.txtAddress) as EditText
        btnFilter = findViewById(R.id.btnFilter) as Button
        btnFilter!!.setOnClickListener({
            createSearch()
        })
        createMap(null)
        createSearch()
    }

    private fun createMap(location: LatLng?) {
        this.repositionMap = true
        this.lastLocation = location
        //this@MapsActivity.llySearch.setVisibility(View.VISIBLE)
        if (mapFragment == null) {
            mapFragment = SupportMapFragment()
            mapFragment!!.getMapAsync(this)
            replaceFragment(mapFragment!!)
        }
    }

    private fun createSearch() {
        llySearch!!.setVisibility(View.INVISIBLE)
        //propertyDefaultFilterFragment = PropertyFilterDialogFragment.Factory.Instance()
        //replaceFragment(propertyDefaultFilterFragment)
    }

    private fun replaceFragment(fragment: Fragment): FragmentTransaction {
        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        if (fragment is DialogFragment) {
            val prev = fm.findFragmentByTag(fragment.getTag())
            if (prev != null) ft.remove(prev)
            ft.addToBackStack(null)
            val dialog = fragment as DialogFragment
            dialog.show(ft, "dialog")
        } else {
            ft.replace(R.id.frameFragmentLayout, fragment)
            ft.commit()
        }
        return ft
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        for (result in grantResults) {
            if (result == PackageManager.PERMISSION_GRANTED) {
                try {
                    this.mMap.isMyLocationEnabled = true
                } catch (ex: SecurityException) {
                    AppHelper.showMessage(this, R.string.dialog_permissions_must_be_allowed)
                }
            }
        }
    }

    fun createDialog(data: PlaceMarkData) {
        //val bottomSheetDialogFragment = PropertyViewPhotoDialogFragment.Factory.Instance(data)
        //bottomSheetDialogFragment.show(supportFragmentManager, bottomSheetDialogFragment.getTag())
    }

    fun drawMarks(marks: List<PlaceMarkData>) {
        try{
            for (mark in marks) {
                var icon = MapsHelper.createBitmap(this, mark)
                if (icon != null){
                    var markOption = MapsHelper.createOptions(this, mark, icon)
                    if (markOption != null){
                        MapsHelper.createMap(mMap, markOption)
                    }
                }
            }
        } catch (ex: Exception){
            LogHelper.addException(ex, "MapsActivity")
        }
    }

    public override fun onMapReady(googleMap: GoogleMap) {
        try
        {
            mMap = googleMap
            mMap.clear()
            mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
            mMap.setOnMarkerClickListener(GoogleMap.OnMarkerClickListener { marker ->
                if (marker.tag != null) {
                    if (marker.tag is PlaceMarkData) {
                        val data = marker.tag as PlaceMarkData?
                        createDialog(data!!)
                        return@OnMarkerClickListener true
                    }
                }
                false
            })
            val marks = PlaceMarksService.placeMarks!!.placemarks
                    .filter { it.latlong() != null }
                    .take(10)
                    .toList();
            drawMarks(marks);

        } catch (ex: Exception){
            LogHelper.addException(ex, "MapsActivity")
        }
    }
}
