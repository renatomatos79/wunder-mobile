package org.wunder

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import org.wunder.data.PlaceMarkData
import android.content.pm.PackageManager
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import org.wunder.helpers.AppHelper
import org.wunder.helpers.LogHelper
import org.wunder.helpers.MapsHelper
import org.wunder.helpers.PlaceMarkDataHelper
import org.wunder.services.PlaceMarksService


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private var lastLocation: LatLng? = null
    private var repositionMap = true
    private var mapFragment: SupportMapFragment? = null
    private var llyInfo: LinearLayout? = null
    private var lblName: TextView? = null
    private var lblLocation: TextView? = null
    private var btnHide: Button? = null;

    private var selectedMark: PlaceMarkData? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        bindControls()
        loadArgments()
        createMap(null)
    }

    private fun loadArgments(){
        val it = intent
        var data = it.getSerializableExtra("data")
        if (data != null && data is PlaceMarkData) {
            selectedMark = data as PlaceMarkData
        }
    }

    private fun bindControls(){
        llyInfo = findViewById(R.id.llyInfo);
        lblName = findViewById(R.id.lblName);
        lblLocation = findViewById(R.id.lblLocation);
        btnHide = findViewById(R.id.btnHide)
        llyInfo!!.setVisibility(View.GONE);
        btnHide!!.setOnClickListener({
            llyInfo!!.setVisibility(View.GONE);
        })
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

    fun showSelectedMark(data: PlaceMarkData) {
        lblName!!.text = data.name
        lblLocation!!.text = data.address
        llyInfo!!.setVisibility(View.VISIBLE);
    }

    fun drawMarks(marks: List<PlaceMarkData>) {
        try{
            for (mark in marks) {
                var icon = MapsHelper.createBitmap(this, mark)
                if (icon != null){
                    var markOption = MapsHelper.createOptions(this, mark, icon)
                    if (markOption != null){
                        MapsHelper.createMap(mMap, mark, markOption)
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
                if (marker.tag != null && marker.tag is PlaceMarkData) {
                    val data = marker.tag as PlaceMarkData?
                    showSelectedMark(data!!)
                    return@OnMarkerClickListener true
                }
                false
            })
            val marks = PlaceMarksService.placeMarks!!.placemarks
                    .filter { PlaceMarkDataHelper.latlong(it) != null }
                    .toList();
            drawMarks(marks);
            gotoSelected(marks)

        } catch (ex: Exception){
            LogHelper.addException(ex, "MapsActivity")
        }
    }

    private fun gotoSelected(marks: List<PlaceMarkData>) {
        try
        {
            // get selected mark sent by list
            var mark: PlaceMarkData? = selectedMark
            if (mark == null){
                mark = marks.firstOrNull();
            }
            if (mark != null){
                var latlng = PlaceMarkDataHelper.latlong(mark!!)
                MapsHelper.gotoLocation(mMap, latlng!!, 33f);
                showSelectedMark(mark)
            }

        } catch (ex: Exception) {
            LogHelper.addException(ex, "MapsActitivy");
        }
    }
}
