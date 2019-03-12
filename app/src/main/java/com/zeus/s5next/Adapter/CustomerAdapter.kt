package com.zeus.s5next.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.zeus.s5next.Data.Custumer

class CustomerAdapter(context: Context, private val data: ArrayList<Custumer>, private val res: Int): BaseAdapter() {

    private val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowview = inflater.inflate(res, parent, false)
        val item = rowview.findViewById<TextView>(android.R.id.text1)
        val datos = data[position]
        item.text = "${datos.nombre} ${datos.apep} ${datos.apem}"

        return rowview
    }

    override fun getItem(position: Int): Any {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return data.size
    }

}