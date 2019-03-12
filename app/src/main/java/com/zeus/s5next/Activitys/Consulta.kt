package com.zeus.s5next.Activitys

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.zeus.s5next.Adapter.CustomerAdapter
import com.zeus.s5next.DB.DB
import com.zeus.s5next.R
import kotlinx.android.synthetic.main.activity_consulta.*

class Consulta : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consulta)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Consulta de clientes"

        val db = DB(this)
        val lista = db.clientes()
        val adapter = CustomerAdapter(this,lista,android.R.layout.simple_list_item_1)
        l_clientes.adapter = adapter

        l_clientes.setOnItemClickListener{_, _, i, _ ->
            val intent = Intent(this, Altas::class.java)
            intent.putExtra("alta","uptade")
            intent.putExtra("id",lista[i].id)
            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
