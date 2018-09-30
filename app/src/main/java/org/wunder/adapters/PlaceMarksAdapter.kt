package org.wunder.adapters

import android.support.v4.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import org.wunder.R
import org.wunder.data.PlaceMarkData
import org.wunder.helpers.PlaceMarkDataHelper
import org.wunder.interfaces.OnItemSelectedListener

class PlaceMarksAdapter(fragment: Fragment, list: List<PlaceMarkData>) : CustomAdapter<PlaceMarkData, PlaceMarksAdapter.PlaceMarkViewHolder>(fragment.activity!!.baseContext, R.layout.fragment_mark, list, false)
{
    var _fragment: Fragment;

    init {
        _fragment = fragment;
    }

    override fun createHolder(model: PlaceMarkData, position: Int, convertView: View?, parent: ViewGroup?): PlaceMarkViewHolder {
        val holder = PlaceMarksAdapter.PlaceMarkViewHolder()
        holder?.lblName = convertView?.findViewById(R.id.lblName)
        holder?.lblFuel = convertView?.findViewById(R.id.lblFuel)
        holder?.lblVin = convertView?.findViewById(R.id.lblVin)
        holder?.lblLocation = convertView?.findViewById(R.id.lblLocation)
        holder?.lblEngineType = convertView?.findViewById(R.id.lblEngineType)
        holder?.imgInterior = convertView?.findViewById(R.id.imgInterior)
        holder?.imgExterior = convertView?.findViewById(R.id.imgExterior)
        holder?.btnViewInMap = convertView?.findViewById(R.id.btnViewInMap)
        holder?.btnViewInMap!!.tag = model

        holder?.btnViewInMap!!.setOnClickListener({
            if (_fragment is OnItemSelectedListener<*>){
                var model = it.tag as PlaceMarkData
                var event = _fragment as OnItemSelectedListener<PlaceMarkData>;
                event.select(model);
            }
        })

        return holder
    }

    override fun updateHolder(holder: PlaceMarkViewHolder, model: PlaceMarkData, position: Int, convertView: View?, parent: ViewGroup?) {
        holder?.lblName?.text = model.name;
        holder?.lblFuel?.text = model.fuel.toString();
        holder?.lblVin?.text = model.vin
        holder?.lblLocation?.text = model.address
        holder?.lblEngineType?.text = model.engineType
        holder?.imgInterior?.setImageResource(PlaceMarkDataHelper.interiorEvaluation(model))
        holder?.imgExterior?.setImageResource(PlaceMarkDataHelper.exteriorEvaluation(model))
    }

    class PlaceMarkViewHolder {
        var lblName: TextView? = null
        var lblFuel: TextView? = null
        var lblVin: TextView? = null
        var lblLocation: TextView? = null
        var lblEngineType: TextView? = null
        var imgInterior: ImageView? = null
        var imgExterior: ImageView? = null
        var btnViewInMap: Button? = null
    }
}