package com.zeus.s5next.Activitys

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.zeus.s5next.Adapter.RegistroAdapter
import com.zeus.s5next.DB.DB
import com.zeus.s5next.R
import kotlinx.android.synthetic.main.activity_resumen.*
import java.util.*

class Resumen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resumen)
        supportActionBar!!.title = "Resumen de pagos"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val db = DB(this)

        e_fechab.setOnClickListener {
            val mcurrentTime = Calendar.getInstance()
            val year = mcurrentTime.get(Calendar.YEAR)
            val mes = mcurrentTime.get(Calendar.MONTH)
            val dia = mcurrentTime.get(Calendar.DAY_OF_MONTH)
            DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                e_fechab.setText(String.format("%d-%02d-%02d",year,month,dayOfMonth))
                val lista = db.pagos(e_fechab.text.toString())
                val adapter = RegistroAdapter(this@Resumen, lista)
                l_pagos.adapter = adapter
            },year,mes,dia).show()
        }

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
