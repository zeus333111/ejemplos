package com.zeus.s5next.Activitys

import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.Toast
import com.zeus.s5next.DB.DB
import com.zeus.s5next.Data.Custumer
import com.zeus.s5next.R
import kotlinx.android.synthetic.main.activity_alta.*

class Altas : AppCompatActivity() {
    private lateinit var alta: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alta)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        if(intent.getStringExtra("alta") == "alta"){
            supportActionBar!!.title = resources.getString(R.string.registro)
            alta = "alta"
        }else{
            supportActionBar!!.title = resources.getString(R.string.editar)
            alta = "update"
            val db = DB(this)
            val cliente = db.cliente(intent.getIntExtra("id",0))

            e_nombre.setText(cliente.nombre)
            e_sgnombre.setText(cliente.segundo_nombre)
            e_apep.setText(cliente.apep)
            e_apem.setText(cliente.apem)
            e_fecnac.setText(cliente.fecnac)
            if (cliente.genero == 1) r_masculino.isChecked = true else r_femenino.isChecked = true
        }


        b_guardar.setOnClickListener {
            validar()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun validar(){
        val views = c_padre.childCount
        var valido = 0
        for (i in 0..views){
            val padre = c_padre.getChildAt(i)
            if( padre is TextInputLayout){
                val edittext = (padre.getChildAt(0) as FrameLayout).getChildAt(0) as TextInputEditText
                if (edittext.text.isNullOrEmpty() && i != 1) edittext.error = "Campo obligatorio" else valido++
            }
        }
        if (r_masculino.isChecked || r_femenino.isChecked) valido++ else
            Toast.makeText(this,"Seleccione genero",Toast.LENGTH_LONG).show()
        if (valido == views -1) guardar()
    }

    private fun guardar(){
        Log.e("Guardar","Se validaron todos los campos y se guardara")
        val db = DB(this)
        val custumer = Custumer(e_nombre.text.toString())
        custumer.id = intent.getIntExtra("id",0)
        custumer.segundo_nombre = e_sgnombre.text.toString()
        custumer.apep = e_apep.text.toString()
        custumer.apem = e_apem.text.toString()
        custumer.fecnac = e_fecnac.text.toString()
        custumer.genero = if(r_masculino.isChecked) 1 else 2

        if(db.guardarCliente(custumer, alta)){
            Toast.makeText(this,"Guardado correctamente",Toast.LENGTH_SHORT).show()
            db.close()
        }
        else{
            Toast.makeText(this,"Error al guardar",Toast.LENGTH_SHORT).show()
            db.close()
        }
    }
}
