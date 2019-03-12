package com.zeus.s5next

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.zeus.s5next.Activitys.Altas
import com.zeus.s5next.Activitys.Consulta
import com.zeus.s5next.Activitys.Registro
import com.zeus.s5next.Activitys.Resumen
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        b_alta.setOnClickListener {
            val intent =  Intent(this, Altas::class.java)
            intent.putExtra("alta","alta")
            startActivity(intent)
        }

        b_consulta.setOnClickListener {
            val intent = Intent(this, Consulta::class.java)
            startActivity(intent)
        }

        b_registro.setOnClickListener {
            val intent = Intent(this, Registro::class.java)
            startActivity(intent)
        }

        b_resumen.setOnClickListener {
            val intent = Intent(this,Resumen::class.java)
            startActivity(intent)
        }
    }
}
