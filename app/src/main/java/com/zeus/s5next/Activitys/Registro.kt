package com.zeus.s5next.Activitys

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.DatePicker
import android.widget.Toast
import com.zeus.s5next.Adapter.CustomerAdapter
import com.zeus.s5next.DB.DB
import com.zeus.s5next.Data.Pagos
import kotlinx.android.synthetic.main.activity_registro.*
import java.text.SimpleDateFormat
import java.util.*


class Registro : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.zeus.s5next.R.layout.activity_registro)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Registro de pagos"

        val db = DB(this)
        val lista = db.clientes()
        val adapter = CustomerAdapter(this,lista, android.R.layout.simple_spinner_dropdown_item)
        val pago = Pagos(0)

        s_clientes.adapter = adapter
        s_clientes.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    pago.id_customer = lista[position].id
            }
        }

        e_fechapago.setOnClickListener {
            val mcurrentTime = Calendar.getInstance()
            val year = mcurrentTime.get(Calendar.YEAR)
            val mes = mcurrentTime.get(Calendar.MONTH)
            val dia = mcurrentTime.get(Calendar.DAY_OF_MONTH)
            DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                e_fechapago.setText(String.format("%d-%02d-%02d",year,month,dayOfMonth))
            },year,mes,dia).show()
        }

        b_registrar.setOnClickListener {
            pago.fechaPago = e_fechapago.text.toString()
            pago.monto = e_monto.text.toString().toDouble()
            pago.fechaReg = SimpleDateFormat("YYYY-MM-DD HH:mm:SS").format(Date())

            if (db.guardarPago(pago) > 0)
                Toast.makeText(this,"Guardado con exito",Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this,"Error Al guardar", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
