package org.wunder.adapters

import android.support.v4.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.wunder.R
import org.wunder.data.PlaceMarkData
import org.wunder.interfaces.OnItemSelectedListener

class PlaceMarksAdapter(fragment: Fragment, list: List<PlaceMarkData>) : CustomAdapter<PlaceMarkData, PlaceMarksAdapter.PlaceMarkViewHolder>(fragment.activity!!.baseContext, R.layout.fragment_mark, list, false)
{
    var _fragment: Fragment;

    init {
        _fragment = fragment;
    }

    override fun createHolder(model: PlaceMarkData, position: Int, convertView: View?, parent: ViewGroup?): PlaceMarkViewHolder {
        val holder = PlaceMarksAdapter.PlaceMarkViewHolder()
        holder?.lblName = convertView?.findViewById(R.id.lblName) as TextView

        holder?.lblName!!.setOnClickListener(View.OnClickListener {
            if (_fragment is OnItemSelectedListener<*>){
                var model = it.tag as PlaceMarkData
                var event = _fragment as OnItemSelectedListener<PlaceMarkData>;
                event.select(model);
            }
        })

        return holder
    }

    override fun updateHolder(holder: PlaceMarkViewHolder, model: PlaceMarkData, position: Int, convertView: View?, parent: ViewGroup?) {
        holder?.lblName?.setText(model.name);
        holder?.lblName?.tag = model
    }

    class PlaceMarkViewHolder {
        var lblName: TextView? = null
    }
}