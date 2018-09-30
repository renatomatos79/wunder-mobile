package org.wunder

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import org.wunder.data.PlaceMarkData
import org.wunder.data.PlaceMarksData
import org.wunder.fragments.PlaceMarksFragment
import org.wunder.helpers.ActivityHelper
import org.wunder.helpers.ActivityHelper.launchActitity
import org.wunder.helpers.DialogHelper


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, PlaceMarksFragment.OnPlaceMarksListener {

    var dialog: ProgressDialog? = null

    override fun complete(marks: PlaceMarksData?, ex: Exception?) {
       // dialog!!.dismiss()
        dialog=null
    }

    override fun selected(item: PlaceMarkData) {
        val it = Intent(this, MapsActivity::class.java)
        it.putExtra("data", item)
        this.startActivity(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideNotificationBar()
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
        loadDeveloperProfile()

        loadMarks(savedInstanceState)
    }

//    fun hideActionBar(hide: Boolean){
//        val bar = supportActionBar
//        if (bar != null){
//            if (hide)
//                bar.hide()
//            else
//                bar.show()
//        }
//    }

    fun hideNotificationBar(){
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    fun loadDeveloperProfile(){
        var lblEmail = nav_view.getHeaderView(0).findViewById<TextView>(R.id.lblEmail)
        lblEmail.setText("renato.matos79@gmail.com")
        var lblUserName = nav_view.getHeaderView(0).findViewById<TextView>(R.id.lblUserName)
        lblUserName.setText("Renato Matos")
    }

    fun loadMarks(savedInstanceState: Bundle?){
//        dialog = DialogHelper.showProgress(this.applicationContext, resources.getString(R.string.dialog_loading_marks), "")
        var fragment = PlaceMarksFragment.newInstance()
        replaceFragment(fragment)
    }

    fun loadMap(savedInstanceState: Bundle?){
        launchActitity(MapsActivity::class.java);
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.mni_list -> {
                loadMarks(null)
            }
            R.id.mni_map -> {
                loadMap(null)
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    fun replaceFragment(fragment: Fragment): FragmentTransaction {
        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        ft.replace(R.id.frmFrameLayout, fragment, "FRAGMENT_MENU")
        ft.commit()
        return ft
    }
}
