package org.wunder.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ListView
import android.widget.TextView

import org.wunder.R
import org.wunder.adapters.PlaceMarksAdapter
import org.wunder.data.PlaceMarkData
import org.wunder.data.PlaceMarksData
import org.wunder.helpers.AppHelper
import org.wunder.helpers.LogHelper
import org.wunder.helpers.PlaceMarkDataHelper
import org.wunder.interfaces.OnDownloadListener
import org.wunder.services.PlaceMarksService

class PlaceMarksFragment : Fragment(), org.wunder.interfaces.OnItemSelectedListener<PlaceMarkData> {

    private var listener: OnPlaceMarksListener? = null
    private var txtSearch: TextView? = null
    private var listMarks: ListView? = null
    private var internalMarks: List<PlaceMarkData>? = null
    private var adapter: PlaceMarksAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun select(item: PlaceMarkData) {
        if (PlaceMarkDataHelper.latlong(item) == null){
            var msg = resources.getString(R.string.place_mark_warning_no_good_coordinators);
            AppHelper.showMessage(activity!!, msg)
        } else {
            if (listener != null){
                listener!!.selected(item);
            }
        }
    }

    private fun filter(content: String?){
        try
        {
            var hasContent = content != null && TextUtils.isEmpty(content) == false
            var filter: List<PlaceMarkData> = internalMarks!!
            if (hasContent){
                filter =  internalMarks!!.filter { it.name.contains(content!!) }.toList()
            }
            if (adapter == null){
                adapter = PlaceMarksAdapter(this@PlaceMarksFragment, filter!!)
                listMarks!!.adapter = adapter
            } else {
                adapter!!.list = filter
                adapter!!.notifyDataSetChanged()
            }
        } catch (ex: Exception){
            LogHelper.addException(ex, "PlaceMarksFragment")
        }
    }

    private fun isDone(actionId: Int) : Boolean {
        return actionId == EditorInfo.IME_ACTION_DONE ||
                actionId == EditorInfo.IME_ACTION_SEARCH ||
                actionId == EditorInfo.IME_ACTION_GO ;
    }

    private fun bindControls(layout: View){
        txtSearch = layout.findViewById(R.id.txtSearch)
        listMarks = layout!!.findViewById<ListView>(R.id.lstMarks)
        txtSearch!!.setOnEditorActionListener() { v, actionId, event ->
            if (isDone(actionId) == true) {
                val content = txtSearch!!.text.toString()
                filter(content)
                true
            } else {
                false
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val layout = inflater.inflate(R.layout.fragment_marks, container, false)
        bindControls(layout!!)
        PlaceMarksService.marks(object: OnDownloadListener<PlaceMarksData> {
            override fun error(ex: Exception) {
                AppHelper.showWarning(activity!!)
                listener!!.complete(null, ex)
            }
            override fun complete(marks: PlaceMarksData) {
                activity!!.runOnUiThread(Runnable {
                    internalMarks = marks.placemarks.toList()
                    filter(null)
                    listener!!.complete(marks, null)
                })
            }
        })
        return layout
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnPlaceMarksListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnPlaceMarksListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnPlaceMarksListener {
        fun complete(marks: PlaceMarksData?, ex: Exception?)
        fun selected(item: PlaceMarkData)
    }

    companion object {
        @JvmStatic
        fun newInstance() = PlaceMarksFragment()
    }
}
