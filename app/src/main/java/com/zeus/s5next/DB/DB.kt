package com.zeus.s5next.DB

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.zeus.s5next.Data.Custumer
import com.zeus.s5next.Data.Pagos
import com.zeus.s5next.Data.Resumenes

class DB(private val context: Context) {
    private val TAG = DB::class.java.simpleName
    private val helper = DbHelper(context)
    lateinit var db: SQLiteDatabase

    fun guardarCliente(custumer: Custumer, alta: String): Boolean{
        Log.e(TAG,"Guardando Cliente")
        val correcto: Boolean
        db = helper.writableDatabase
        val values = ContentValues()
        values.put(DbHelper.Customer.NAME,custumer.nombre)
        values.put(DbHelper.Customer.MIDDLE_NAME, custumer.segundo_nombre)
        values.put(DbHelper.Customer.LAST_NAME, custumer.apep)
        values.put(DbHelper.Customer.SECOND_LAST_NAME, custumer.apem)
        values.put(DbHelper.Customer.BIRTHDATE, custumer.fecnac)
        values.put(DbHelper.Customer.GENDER, custumer.genero)

        correcto = if(alta == "alta")
            db.insert(DbHelper.Customer.TABLA_CUSTOMER,null,values) > 0
        else
            db.update(DbHelper.Customer.TABLA_CUSTOMER,values,"${DbHelper.Customer.ID_CUSTOMER}=${custumer.id}",null) > 0

        return correcto
    }

    fun guardarPago(pago: Pagos): Long{
        Log.e(TAG,"Guardando un pago")
        db = helper.writableDatabase
        val values =  ContentValues()
        values.put(DbHelper.Payments.ID_CUSTOMER,pago.id_customer)
        values.put(DbHelper.Payments.REGISTER_DATE,pago.fechaReg)
        values.put(DbHelper.Payments.DATE,pago.fechaPago)
        values.put(DbHelper.Payments.AMOUNT,pago.monto)

        return db.insert(DbHelper.Payments.TABLA_PAYMENTS,null,values)
    }

    fun cliente(id: Int): Custumer{
        Log.e(TAG,"buscando cleinte $id")
        db = helper.readableDatabase
        val query = "select * from ${DbHelper.Customer.TABLA_CUSTOMER} where ${DbHelper.Customer.ID_CUSTOMER} = $id"
        val c = db.rawQuery(query, null)
        var cus: Custumer? = null

        if (c.moveToFirst()){
            do{
                cus = Custumer(c.getString(1))
                cus.id = c.getInt(0)
                cus.segundo_nombre = c.getString(2)
                cus.apep = c.getString(3)
                cus.apem = c.getString(4)
                cus.fecnac = c.getString(5)
                cus.genero = c.getInt(6)
            }while(c.moveToNext())
        }
        db.close()
        return cus!!
    }

    fun pagos(fecha: String): ArrayList<Resumenes>{
        Log.e(TAG,"Obteniendo resumen")
        val pay = DbHelper.Payments
        val cus = DbHelper.Customer
        db = helper.readableDatabase
        val query = "select ${cus.NAME}||' '||${cus.LAST_NAME}||' '||${cus.SECOND_LAST_NAME}," +
                "sum(${pay.AMOUNT}), count(${pay.AMOUNT}), ${cus.TABLA_CUSTOMER}.${cus.ID_CUSTOMER} from ${pay.TABLA_PAYMENTS} " +
                "join ${cus.TABLA_CUSTOMER} on ${pay.TABLA_PAYMENTS}.${pay.ID_CUSTOMER}=${cus.TABLA_CUSTOMER}.${cus.ID_CUSTOMER} " +
                "where ${pay.DATE} = '$fecha' group by ${cus.TABLA_CUSTOMER}.${cus.ID_CUSTOMER}"
        Log.e(TAG, "query: $query")
        val c = db.rawQuery(query,null)
        val pagos = ArrayList<Resumenes>()

        if (c.moveToFirst() && c.getInt(2) > 0){
            do {
                val res= Resumenes(c.getString(0))
                res.pagos = c.getInt(2)
                res.total = c.getDouble(1)
                pagos.add(res)
            }while (c.moveToNext())
        }
        return pagos
    }

    fun clientes(): ArrayList<Custumer>{
        Log.e(TAG,"obteninedo datos de clientes")
        db = helper.readableDatabase
        val query = "SELECT * FROM ${DbHelper.Customer.TABLA_CUSTOMER}"
        val c = db.rawQuery(query, null)
        val customers = ArrayList<Custumer>()

        if (c.moveToFirst()){
            do{
                val customer = Custumer(c.getString(1))
                customer.id = c.getInt(0)
                customer.segundo_nombre = c.getString(2)
                customer.apep = c.getString(3)
                customer.apem = c.getString(4)
                customer.fecnac = c.getString(5)
                customer.genero = c.getInt(6)
                customers.add(customer)
            }while(c.moveToNext())
        }
        db.close()
        return customers
    }

    fun close(){
        db.close()
    }
}