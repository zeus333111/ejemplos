package com.zeus.s5next.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.zeus.s5next.Data.Resumenes
import com.zeus.s5next.R

class RegistroAdapter(context: Context, private val data: ArrayList<Resumenes>): BaseAdapter() {

    private val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowview = inflater.inflate(R.layout.lista_pagos,parent,false)
        val datos = data[position]

        val nombre = rowview.findViewById<TextView>(R.id.lt_cliente)
        val pagos = rowview.findViewById<TextView>(R.id.lt_pagos)
        val total = rowview.findViewById<TextView>(R.id.lt_total)

        nombre.text = datos.nombre
        pagos.text = "Total de pagos: ${datos.pagos}"
        total.text = "Monto total: ${datos.total}"

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