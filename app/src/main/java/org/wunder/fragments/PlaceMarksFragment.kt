package org.wunder.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView

import org.wunder.R
import org.wunder.adapters.PlaceMarksAdapter
import org.wunder.data.PlaceMarksData
import org.wunder.helpers.AppHelper
import org.wunder.interfaces.OnDownloadListener
import org.wunder.services.PlaceMarksService

class PlaceMarksFragment : Fragment() {
    private var listener: OnPlaceMarksListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val layout = inflater.inflate(R.layout.fragment_marks, container, false)
        PlaceMarksService.marks(object: OnDownloadListener<PlaceMarksData> {
            override fun error(ex: Exception) {
                AppHelper.showWarning(activity!!)
                listener!!.complete(null, ex)
            }
            override fun complete(marks: PlaceMarksData) {
                activity!!.runOnUiThread(Runnable {
                    var adapter = PlaceMarksAdapter(this@PlaceMarksFragment, marks.placemarks)
                    val listMarks = layout.findViewById<ListView>(R.id.lstMarks)
                    listMarks.adapter = adapter
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
    }

    companion object {
        @JvmStatic
        fun newInstance() = PlaceMarksFragment()
    }
}
