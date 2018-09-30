package org.wunder.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import org.wunder.interfaces.OnItemSelectedListener

abstract class CustomAdapter<T : Any, Holder : Any>(val ctx: Context, val resourceId: Int, val list: List<T>, var enableRowClick: Boolean = true) : ArrayAdapter<T>(ctx, resourceId, list)
{
    abstract fun createHolder(model: T, position: Int, convertView: View?, parent: ViewGroup?) : Holder;

    abstract fun updateHolder(holder: Holder, model: T, position: Int, convertView: View?, parent: ViewGroup?);

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        val model = list[position]
        var holder: Holder
        var convertView: View
        // se a view for nula, entao localiza a view pelo layout que sera sobreescrito pela classe filha e a joga na tag
        if (view == null) {
            // inicializa a view que sera handleClick em cache via TAG
            convertView = LayoutInflater.from(this.ctx).inflate(this.resourceId, parent, false)
            if (enableRowClick){
                convertView.setOnClickListener(View.OnClickListener {
                    // se o contexto implementa a interface OnItemSelectedListener entao dispara o evento
                    if (this.ctx is OnItemSelectedListener<*>) {
                        val listener = context as OnItemSelectedListener<T>
                        listener.select(model)
                    }
                })
            }
            holder = createHolder(model, position, convertView, parent)
            convertView.tag = holder
        } else {
            convertView = view!!
            holder = view.tag as Holder
        }
        if (holder != null) {
            updateHolder(holder, model, position, view, parent)
        }
        // retorna a view ja populada
        return convertView
    }

    override fun getItem(position: Int): T {
        return this.list.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return this.list.count()
    }

}